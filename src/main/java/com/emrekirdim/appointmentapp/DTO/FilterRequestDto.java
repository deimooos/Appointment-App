package com.emrekirdim.appointmentapp.DTO;

import com.emrekirdim.appointmentapp.Models.AppointmentStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FilterRequestDto {
    private Long userId;
    private Long doctorId;
    private Long specialtyId;
    private LocalDateTime start;
    private LocalDateTime end;
    private AppointmentStatus status;
    private String result;
}
