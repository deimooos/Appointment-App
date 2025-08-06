package com.emrekirdim.appointmentapp.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
public class DoctorAvailabilityRequestDto {
    @NotNull(message = "Doctor ID cannot be null.")
    @Positive(message = "Doctor ID must be a positive number")
    private Long doctorId;

    @NotNull(message = "Date time cannot be null.")
    private LocalDateTime dateTime;

}

