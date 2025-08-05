package com.emrekirdim.appointmentapp.Models;

import com.emrekirdim.appointmentapp.Models.Enums.AppointmentResult;
import com.emrekirdim.appointmentapp.Models.Enums.AppointmentStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FilterRequest {
    private Long userId;
    private Long doctorId;
    private LocalDateTime start;
    private LocalDateTime end;
    private AppointmentStatus status;
    private AppointmentResult result;
    private Long specialtyId;

}
