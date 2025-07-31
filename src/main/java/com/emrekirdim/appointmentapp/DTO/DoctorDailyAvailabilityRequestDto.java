package com.emrekirdim.appointmentapp.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
public class DoctorDailyAvailabilityRequestDto {

    @Positive(message = "Doctor ID must be a positive number")
    @NotNull(message = "Doctor ID cannot be null")
    private Long doctorId;

    @NotNull(message = "Date must be provided")
    private LocalDate date;

}
