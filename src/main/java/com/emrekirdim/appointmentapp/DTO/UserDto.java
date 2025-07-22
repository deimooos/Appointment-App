package com.emrekirdim.appointmentapp.DTO;

import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Data
public class UserDto {

    private Long id;

    @NotBlank(message = "Name cannot be blank.")
    @Pattern(
            regexp = "^([A-ZÇĞİÖŞÜ][a-zçğıöşü]+)(\\s[A-ZÇĞİÖŞÜ][a-zçğıöşü]+)+$",
            message = "Name must contain at least two words, each starting with an uppercase letter followed by lowercase letters. Turkish characters are supported."
    )
    private String name;

    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Email must be in a valid format.")
    private String email;

    @NotBlank(message = "Phone number cannot be blank.")
    @Pattern(
            regexp = "^05(0[0-9]|3[0-9]|4[0-9]|5[0-9])\\d{7}$",
            message = "Phone number must start with a valid Turkish GSM prefix (e.g., 530, 540) and be 11 digits long."
    )
    private String phone;

    @NotBlank(message = "ID number cannot be blank.")
    @Pattern(
            regexp = "^[1-9][0-9]{9}[02468]$",
            message = "ID number must be 11 digits, start with a non-zero digit, and end with an even digit."
    )
    private String idNum;
}
