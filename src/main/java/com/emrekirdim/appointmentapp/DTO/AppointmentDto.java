package com.emrekirdim.appointmentapp.DTO;

import com.emrekirdim.appointmentapp.Models.AppointmentResult;
import com.emrekirdim.appointmentapp.Models.AppointmentStatus;
import javax.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDto {

    private Long id;

    @NotNull(message = "UserId cannot be null.")
    @Positive(message = "UserId must be a positive number.")
    private Long userId;

    @NotNull(message = "DoctorId cannot be null.")
    @Positive(message = "DoctorId must be a positive number.")
    private Long doctorId;

    @NotNull(message = "DateTime cannot be null.")
    @FutureOrPresent(message = "DateTime cannot be in the past.")
    private LocalDateTime dateTime;

    @NotNull(message = "Status cannot be null.")
    private AppointmentStatus status;

    @NotNull(message = "Result cannot be null.")
    private AppointmentResult result;
}
