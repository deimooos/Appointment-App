package com.emrekirdim.appointmentapp.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class UserUpdateDto {

    @NotNull(message = "User ID cannot be null.")
    private Long id;

    @Pattern(
            regexp = "^[A-ZÇİÖŞÜ][a-zçğıöşü]{1,20}(\\s[A-ZÇİÖŞÜ][a-zçğıöşü]{1,20})*$",
            message = "Name must contain one or more words, each starting with an uppercase letter followed by lowercase letters."
    )
    @Schema(type = "string", example = "Mehmet")
    private String name;

    @Pattern(
            regexp = "^[A-ZÇİÖŞÜ][a-zçğıöşü]{1,20}$",
            message = "Surname must start with an uppercase letter followed by lowercase letters."
    )
    @Schema(type = "string", example = "Gündüz")
    private String surname;

    @Email(message = "Email must be in a valid format.")
    @Schema(type = "string", example = "mehmet@example.com")
    private String email;

    @Pattern(
            regexp = "^05(0[0-9]|3[0-9]|4[0-9]|5[0-9])\\d{7}$",
            message = "Phone number must start with a valid Turkish GSM prefix (e.g., 530, 540) and be 11 digits long."
    )
    private String phone;

    @Pattern(
            regexp = "^[1-9][0-9]{9}[02468]$",
            message = "ID number must be 11 digits, start with a non-zero digit, and end with an even digit."
    )
    private String idNum;
}
