package com.emrekirdim.appointmentapp.DTO;

import lombok.Data;

@Data
public class UserResponseDto {

    private Long id;

    private String name;

    private String surname;

    private String email;

    private String phone;

    private String idNum;

}
