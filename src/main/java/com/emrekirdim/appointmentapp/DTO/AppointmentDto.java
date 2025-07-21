package com.emrekirdim.appointmentapp.DTO;

import com.emrekirdim.appointmentapp.Models.AppointmentResult;
import com.emrekirdim.appointmentapp.Models.AppointmentStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDto {
    private Long id;
    private Long userId;
    private Long doctorId;
    private LocalDateTime dateTime;
    private AppointmentStatus status;
    private AppointmentResult result;
}
