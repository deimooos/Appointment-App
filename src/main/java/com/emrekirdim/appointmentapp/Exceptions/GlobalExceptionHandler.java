package com.emrekirdim.appointmentapp.Exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Internal server error occurred.");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Map<String, String> error = new HashMap<>();

        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException) {
            InvalidFormatException ife = (InvalidFormatException) cause;
            String fieldName = "";
            if (!ife.getPath().isEmpty()) {
                fieldName = ife.getPath().get(0).getFieldName();
            }

            Class<?> targetType = ife.getTargetType();
            if (targetType != null && targetType.isEnum()) {
                error.put("error", "Invalid value for field '" + fieldName + "'. Allowed values are: " + enumValuesAsString(targetType));
            } else if ("LocalDateTime".equals(targetType != null ? targetType.getSimpleName() : "")) {
                error.put("error", "Invalid format for field '" + fieldName + "'. Please use the format 'yyyy-MM-ddTHH:mm:ss'.");
            } else {
                error.put("error", "Invalid format for field '" + fieldName + "'.");
            }
        } else {
            error.put("error", "Malformed JSON request.");
        }

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    private String enumValuesAsString(Class<?> enumClass) {
        Object[] enumConstants = enumClass.getEnumConstants();
        StringBuilder sb = new StringBuilder();
        for (Object constant : enumConstants) {
            sb.append(constant.toString()).append(", ");
        }
        if (sb.length() > 2) {
            sb.setLength(sb.length() - 2); // son virgül ve boşluk sil
        }
        return sb.toString();
    }
}
