package com.emrekirdim.appointmentapp.DTO;

import com.emrekirdim.appointmentapp.Models.Enums.AppointmentResult;
import com.emrekirdim.appointmentapp.Models.Enums.AppointmentStatus;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class FilterRequestDto {

    @Positive(message = "User ID must be a positive number")
    private Long userId;

    @Positive(message = "Doctor ID must be a positive number")
    private Long doctorId;

    @Positive(message = "Specialty ID must be a positive number")
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
