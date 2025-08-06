package com.emrekirdim.appointmentapp.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Data
public class DoctorUpdateDto {

    @NotNull(message = "Doctor ID cannot be null.")
    @Positive(message = "Doctor ID must be a positive number")
    private Long id;

    @Pattern(
            regexp = "^(Prof\\. (Dr\\.)?|Doç\\. (Dr\\.)?|Yrd\\. Doç\\. (Dr\\.)?|Uzm\\. (Dr\\.)?|Op\\. (Dr\\.)?|Pratisyen Dr\\.|Dr\\.)$",
            message = "Title must be a valid Turkish medical title like Dr., Doç. Dr., Prof. Dr., etc."
    )
    @Schema(type = "string", example = "Prof. Dr.")
    private String title;

    @Pattern(
            regexp = "^([A-ZÇİÖŞÜ][a-zçğıöşü]+)(\\s[A-ZÇİÖŞÜ][a-zçğıöşü]+)*$",
            message = "Name must contain one or more words, each starting with an uppercase letter followed by lowercase letters."
    )
    @Schema(type = "string", example = "Ahmet")
    private String name;

    @Pattern(
            regexp = "^[A-ZÇİÖŞÜ][a-zçğıöşü]{1,20}$",
            message = "Surname must start with an uppercase letter followed by lowercase letters."
    )
    @Schema(type = "string", example = "Yılmaz")
    private String surname;

    @Positive(message = "Specialty ID must be a positive number")
    private Long specialtyId;
}
