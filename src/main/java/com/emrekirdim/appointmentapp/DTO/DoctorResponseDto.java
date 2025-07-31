package com.emrekirdim.appointmentapp.DTO;

import lombok.Data;

@Data
public class DoctorResponseDto {

    private Long id;

    private String title;

    private String name;

    private String surname;

    private Long specialtyId;
}
