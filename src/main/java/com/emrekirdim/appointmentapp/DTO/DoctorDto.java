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

    @NotBlank(message = "Title cannot be blank")
    @Pattern(
            regexp = "^(Dr\\.|Doç\\.|Prof\\.|Uzm\\.|Yrd\\. Doç\\.)$",
            message = "Title must be a valid Turkish medical title like Dr., Doç., Prof., etc."
    )
    private String title;

    @NotBlank(message = "Doctor name cannot be empty")
    @Pattern(
            regexp = "^([A-ZÇİÖŞÜ][a-zçğıöşü]+)(\\s[A-ZÇĞİÖŞÜ][a-zçğıöşü]+)*$",
            message = "Each word in the name must start with an uppercase letter and contain only Turkish letters."
    )
    private String name;

    @NotBlank(message = "Doctor surname cannot be empty.")
    @Pattern(
            regexp = "^[A-ZÇİÖŞÜ][a-zçğıöşü]{1,20}$",
            message = "Surname must start with a capital letter and contain only Turkish letters."
    )
    private String surname;

    @NotNull(message = "Specialty ID is required")
    private Long specialtyId;
}
