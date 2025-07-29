package com.emrekirdim.appointmentapp.DTO;

import com.emrekirdim.appointmentapp.Models.AppointmentResult;
import com.emrekirdim.appointmentapp.Models.AppointmentStatus;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
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

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime start;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime end;

    private AppointmentStatus status;

    private AppointmentResult result;

    @AssertTrue(message = "Start date must be before end date.")
    public boolean isDateRangeValid() {
        if (start == null || end == null) {
            return true;
        }
        return start.isBefore(end);
    }
}
