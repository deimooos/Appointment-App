package com.emrekirdim.appointmentapp.DTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecialtyDto {

    private Long id;

    @NotBlank(message = "Specialty name cannot be blank")
    @Pattern(
            regexp = "^[A-Z][a-z]*(\\s[A-Z][a-z]*)*$",
            message = "Specialty name must be valid."
    )
    private String name;
}


