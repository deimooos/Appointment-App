package com.emrekirdim.appointmentapp.DTO;

import com.emrekirdim.appointmentapp.Models.Enums.AppointmentResult;
import com.emrekirdim.appointmentapp.Models.Enums.AppointmentStatus;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class FilterRequestDto {

    @Min(value = 1, message = "User ID must be greater than 0.")
    private Long userId;

    @Min(value = 1, message = "Doctor ID must be greater than 0.")
    private Long doctorId;

    @Min(value = 1, message = "Specialty ID must be greater than 0.")
    private Long specialtyId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime start;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime end;

    private AppointmentStatus status;

    private AppointmentResult result;

    @Schema(hidden = true)
    @AssertTrue(message = "Start date must be before end date.")
    public boolean isDateRangeValid() {
        if (start == null || end == null) {
            return true;
        }
        return start.isBefore(end);
    }
}
