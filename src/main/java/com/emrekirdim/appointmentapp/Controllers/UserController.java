package com.emrekirdim.appointmentapp.Controllers;

import com.emrekirdim.appointmentapp.DTO.UserDto;
import com.emrekirdim.appointmentapp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserDto userDto) {
        userService.createUser(userDto);
        return ResponseEntity.ok("User created successfully.");
    }

    @GetMapping("/all")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@Valid @RequestBody UserDto userDto) {
        userService.updateUser(userDto);
        return ResponseEntity.ok("User updated successfully.");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@Valid @RequestBody UserDto userDto) {
        userService.deleteUser(userDto);
        return ResponseEntity.ok("User deleted successfully.");
    }
}
