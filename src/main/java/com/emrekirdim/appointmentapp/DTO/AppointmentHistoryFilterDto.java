package com.emrekirdim.appointmentapp.DTO;

import com.emrekirdim.appointmentapp.Models.Enums.AppointmentStatus;
import com.emrekirdim.appointmentapp.Models.Enums.AppointmentResult;
import lombok.Data;


@Data
public class AppointmentHistoryFilterDto {
    private Long userId;
    private AppointmentTimeType timeType;
    private AppointmentStatus status;
    private AppointmentResult result;

    public enum AppointmentTimeType {
        PAST,
        UPCOMING,
        ALL
    }
}
