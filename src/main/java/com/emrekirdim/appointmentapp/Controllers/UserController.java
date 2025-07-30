package com.emrekirdim.appointmentapp.Controllers;

import com.emrekirdim.appointmentapp.DTO.UserDto;
import com.emrekirdim.appointmentapp.Services.GenericService;
import com.emrekirdim.appointmentapp.Services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User Controller", description = "Endpoints for managing users")
@RestController
@RequestMapping("/api/users")
public class UserController extends GenericController<UserDto, Long> {

    @Autowired
    private UserService userService;

    @Override
    protected GenericService<UserDto, Long> getService() {
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
