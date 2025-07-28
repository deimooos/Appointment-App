package com.emrekirdim.appointmentapp.Controllers;

import com.emrekirdim.appointmentapp.DTO.UserDto;
import com.emrekirdim.appointmentapp.Services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "User Controller", description = "Endpoints for managing users")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Create a new user", description = "Creates a new user with the provided details.")
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserDto userDto) {
        userService.createUser(userDto);
        return ResponseEntity.ok("User created successfully.");
    }

    @Operation(summary = "Retrieve all users", description = "Returns a list of all users.")
    @GetMapping("/all")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Update an existing user", description = "Updates user information based on provided details.")
    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@Valid @RequestBody UserDto userDto) {
        userService.updateUser(userDto);
        return ResponseEntity.ok("User updated successfully.");
    }

    @Operation(summary = "Delete a user", description = "Deletes a user based on the provided user data.")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestBody UserDto userDto) {
        userService.deleteUser(userDto);
        return ResponseEntity.ok("User deleted successfully.");
    }
}
