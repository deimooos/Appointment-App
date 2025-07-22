package com.emrekirdim.appointmentapp.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {
    private Long id;

    @NotBlank(message = "Doctor name cannot be empty")
    @Pattern(
            regexp = "^(?:(?:Dr\\.|Prof\\.|Mr\\.|Mrs\\.|Ms\\.)\\s+)+[A-ZÇĞİÖŞÜ][a-zçğıöşü]+(?:\\s[A-ZÇĞİÖŞÜ][a-zçğıöşü]+)+$",
            message = "Name must start with one or more titles (e.g. Prof. Dr.) followed by at least two words starting with a capital letter, without trailing dots."
    )
    private String name;

    @Valid
    @NotNull(message = "Specialty must be provided")
    private SpecialtyDto specialty;
}
