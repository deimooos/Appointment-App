package com.emrekirdim.appointmentapp.Controllers;

import com.emrekirdim.appointmentapp.DTO.UserCreateDto;
import com.emrekirdim.appointmentapp.DTO.UserResponseDto;
import com.emrekirdim.appointmentapp.DTO.UserUpdateDto;
import com.emrekirdim.appointmentapp.Services.AdvancedGenericService;
import com.emrekirdim.appointmentapp.Services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Patient - User Management", description = "Endpoints for patients to register, update profile information, and manage their accounts.")
@RestController
@RequestMapping("/api/users")
public class UserController extends AdvancedGenericController<UserCreateDto, UserUpdateDto, UserResponseDto, Long> {

    @Autowired
    private UserService userService;

    @Override
    protected AdvancedGenericService<UserCreateDto, UserUpdateDto, UserResponseDto, Long> getService() {
        return userService;
    }

    @Override
    protected String getCreateMessage() {
        return "User created successfully.";
    }

    @Override
    protected String getUpdateMessage() {
        return "User updated successfully.";
    }

    @Override
    protected String getDeleteMessage() {
        return "User deleted successfully.";
    }
}
