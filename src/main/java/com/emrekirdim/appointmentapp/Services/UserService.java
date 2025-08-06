package com.emrekirdim.appointmentapp.Services;

import com.emrekirdim.appointmentapp.DTO.UserCreateDto;
import com.emrekirdim.appointmentapp.DTO.UserResponseDto;
import com.emrekirdim.appointmentapp.DTO.UserUpdateDto;
import com.emrekirdim.appointmentapp.Models.User;
import com.emrekirdim.appointmentapp.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements AdvancedGenericService<UserCreateDto, UserUpdateDto, UserResponseDto, Long> {

    @Autowired
    private UserRepository userRepository;

    public void checkAnyUserExists() throws IllegalArgumentException{
        if (!userRepository.existsAnyUser()) {
            throw new IllegalArgumentException("No users found in the system.");
        }
    }

    private User mapToEntity(UserCreateDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setIdNum(dto.getIdNum());
        return user;
    }

    private UserResponseDto mapToResponse(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setIdNum(user.getIdNum());
        return dto;
    }

    @Override
    public UserResponseDto create(UserCreateDto userDto) throws IllegalArgumentException{
        User user = mapToEntity(userDto);

        if (!isValidTurkishId(user.getIdNum())) {
            throw new IllegalArgumentException("Invalid identification number.");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email is already registered.");
        }
        if (userRepository.existsByPhone(user.getPhone())) {
            throw new IllegalArgumentException("Phone number is already registered.");
        }
        if (userRepository.existsByIdNum(user.getIdNum())) {
            throw new IllegalArgumentException("Identification number is already registered.");
        }

        User savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    @Override
    public List<UserResponseDto> getAll() {
        checkAnyUserExists();
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto update(UserUpdateDto userDto) throws IllegalArgumentException{
        if (userDto.getId() == null) {
            throw new IllegalArgumentException("User id must not be null.");
        }

        User existingUser = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userDto.getId()));

        if (userDto.getName() != null) existingUser.setName(userDto.getName());
        if (userDto.getSurname() != null) existingUser.setSurname(userDto.getSurname());
        if (userDto.getEmail() != null) existingUser.setEmail(userDto.getEmail());
        if (userDto.getPhone() != null) existingUser.setPhone(userDto.getPhone());
        if (userDto.getIdNum() != null) existingUser.setIdNum(userDto.getIdNum());

        if (userRepository.existsByEmailAndIdNot(existingUser.getEmail(), existingUser.getId())) {
            throw new IllegalArgumentException("Email is already registered.");
        }
        if (userRepository.existsByPhoneAndIdNot(existingUser.getPhone(), existingUser.getId())) {
            throw new IllegalArgumentException("Phone number is already registered.");
        }
        if (userRepository.existsByIdNumAndIdNot(existingUser.getIdNum(), existingUser.getId())) {
            throw new IllegalArgumentException("Identification number is already registered.");
        }

        User savedUser = userRepository.save(existingUser);
        return mapToResponse(savedUser);
    }

    @Override
    public void delete(Long id) throws IllegalArgumentException {
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

    public User getEntityById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("User id must not be null.");
        }
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }

    public boolean existsById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("User id must not be null.");
        }
        return userRepository.existsById(id);
    }

    public boolean existsAnyUser() {
        return userRepository.count() > 0;
    }




}
