package com.emrekirdim.appointmentapp.Models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DateRange {
    private LocalDateTime start;
    private LocalDateTime end;
}
