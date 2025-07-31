package com.emrekirdim.appointmentapp.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class DoctorUpdateDto {

    @NotNull(message = "Doctor ID cannot be null.")
    private Long id;

    @Pattern(
            regexp = "^(Prof\\. (Dr\\.)?|Doç\\. (Dr\\.)?|Yrd\\. Doç\\. (Dr\\.)?|Uzm\\. (Dr\\.)?|Op\\. (Dr\\.)?|Pratisyen Dr\\.|Dr\\.)$",
            message = "Title must be a valid Turkish medical title like Dr., Doç. Dr., Prof. Dr., etc."
    )
    private String title;

    @Pattern(
            regexp = "^([A-ZÇİÖŞÜ][a-zçğıöşü]+)(\\s[A-ZÇİÖŞÜ][a-zçğıöşü]+)*$",
            message = "Name must contain one or more words, each starting with an uppercase letter followed by lowercase letters."
    )
    private String name;

    @Pattern(
            regexp = "^[A-ZÇİÖŞÜ][a-zçğıöşü]{1,20}$",
            message = "Surname must start with an uppercase letter followed by lowercase letters."
    )
    private String surname;

    private Long specialtyId;
}
