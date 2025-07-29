package com.emrekirdim.appointmentapp.DTO;

import javax.validation.constraints.*;

import com.emrekirdim.appointmentapp.Models.AppointmentResult;
import com.emrekirdim.appointmentapp.Models.AppointmentStatus;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.time.LocalDateTime;

@Data
public class AppointmentDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
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

    @JsonProperty(access = Access.READ_ONLY)
    private AppointmentStatus status;

    @JsonProperty(access = Access.READ_ONLY)
    private AppointmentResult result;
}
