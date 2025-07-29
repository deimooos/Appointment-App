package com.emrekirdim.appointmentapp.Services;

import com.emrekirdim.appointmentapp.DTO.UserDto;
import com.emrekirdim.appointmentapp.Models.User;
import com.emrekirdim.appointmentapp.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void checkAnyUserExists() {
        if (!userRepository.existsAnyUser()) {
            throw new IllegalArgumentException("No users found in the system.");
        }
    }

    private User mapToEntity(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setIdNum(dto.getIdNum());
        return user;
    }

    private UserDto mapToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setIdNum(user.getIdNum());
        return dto;
    }

    public UserDto createUser(UserDto userDto) {
        User user = mapToEntity(userDto);

        if (!isValidTurkishId(user.getIdNum())) {
            throw new IllegalArgumentException("Invalid identification number.");
        }

        if(userRepository.existsByNameAndSurname(user.getName(), user.getSurname())) {
            throw new IllegalArgumentException("User with this name and surname already exists.");
        }

        if(userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email is already registered.");
        }

        if(userRepository.existsByPhone(user.getPhone())) {
            throw new IllegalArgumentException("Phone number is already registered.");
        }

        if(userRepository.existsByIdNum(user.getIdNum())) {
            throw new IllegalArgumentException("Identification number is already registered.");
        }

        User savedUser = userRepository.save(user);
        return mapToDto(savedUser);
    }

    public List<UserDto> getAllUsers() {
        checkAnyUserExists();
        return userRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public UserDto updateUser(UserDto userDto) {
        User user = mapToEntity(userDto);

        if (!isValidTurkishId(user.getIdNum())) {
            throw new IllegalArgumentException("Invalid idetification number.");
        }

        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + user.getId()));

        boolean existsNameSurname = userRepository.existsByNameAndSurname(user.getName(), user.getSurname())
                && (!existingUser.getName().equals(user.getName()) || !existingUser.getSurname().equals(user.getSurname()));

        boolean existsEmail = userRepository.existsByEmail(user.getEmail())
                && !existingUser.getEmail().equals(user.getEmail());

        boolean existsPhone = userRepository.existsByPhone(user.getPhone())
                && !existingUser.getPhone().equals(user.getPhone());

        boolean existsIdNum = userRepository.existsByIdNum(user.getIdNum())
                && !existingUser.getIdNum().equals(user.getIdNum());

        if(existsNameSurname) {
            throw new IllegalArgumentException("User with this name and surname already exists.");
        }
        if(existsEmail) {
            throw new IllegalArgumentException("Email is already registered.");
        }
        if(existsPhone) {
            throw new IllegalArgumentException("Phone number is already registered.");
        }
        if(existsIdNum) {
            throw new IllegalArgumentException("Identification number is already registered.");
        }

        User updatedUser = userRepository.save(user);
        return mapToDto(updatedUser);
    }

    public void deleteUser(UserDto userDto) {
        Long id = userDto.getId();
        if (id == null) {
            throw new IllegalArgumentException("User id must not be null.");
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        userRepository.delete(user);
    }

    private boolean isValidTurkishId(String id) {
        if (id == null || !id.matches("^[1-9][0-9]{10}$")) {
            return false;
        }

        int[] digits = new int[11];
        for (int i = 0; i < 11; i++) {
            digits[i] = Character.getNumericValue(id.charAt(i));
        }

        int sumOdd = digits[0] + digits[2] + digits[4] + digits[6] + digits[8];
        int sumEven = digits[1] + digits[3] + digits[5] + digits[7];

        int digit10 = ((sumOdd * 7) - sumEven) % 10;
        if (digit10 != digits[9]) return false;

        int total = 0;
        for (int i = 0; i < 10; i++) {
            total += digits[i];
        }

        int digit11 = total % 10;
        return digit11 == digits[10];
    }

}
