package com.emrekirdim.appointmentapp.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorCreateDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "Title cannot be blank.")
    @Pattern(
            regexp = "^(Prof\\. (Dr\\.)?|Doç\\. (Dr\\.)?|Yrd\\. Doç\\. (Dr\\.)?|Uzm\\. (Dr\\.)?|Op\\. (Dr\\.)?|Pratisyen Dr\\.|Dr\\.)$",
            message = "Title must be a valid Turkish medical title like Dr., Doç. Dr., Prof. Dr., etc."
    )
    @Schema(type = "string", example = "Prof. Dr.")
    private String title;

    @NotBlank(message = "Doctor name cannot be empty.")
    @Pattern(
            regexp = "^([A-ZÇİÖŞÜ][a-zçğıöşü]+)(\\s[A-ZÇİÖŞÜ][a-zçğıöşü]+)*$",
            message = "Name must contain one or more words, each starting with an uppercase letter followed by lowercase letters."
    )
    @Schema(type = "string", example = "Ahmet")
    private String name;

    @NotBlank(message = "Doctor surname cannot be empty.")
    @Pattern(
            regexp = "^[A-ZÇİÖŞÜ][a-zçğıöşü]{1,20}$",
            message = "Surname must start with an uppercase letter followed by lowercase letters."
    )
    @Schema(type = "string", example = "Yılmaz")
    private String surname;

    @NotNull(message = "Specialty ID is required.")
    @Positive(message = "Specialty ID must be a positive number")
    private Long specialtyId;
}
