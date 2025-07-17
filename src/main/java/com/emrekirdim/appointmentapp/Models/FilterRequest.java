package com.emrekirdim.appointmentapp.Models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FilterRequest {
    private Long userId;
    private Long doctorId;
    private LocalDateTime start;
    private LocalDateTime end;
    private AppointmentStatus status;
    private String result;

}
