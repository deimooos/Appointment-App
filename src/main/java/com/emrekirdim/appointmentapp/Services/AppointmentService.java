package com.emrekirdim.appointmentapp.Services;

import com.emrekirdim.appointmentapp.DTO.AppointmentDto;
import com.emrekirdim.appointmentapp.DTO.FilterRequestDto;
import com.emrekirdim.appointmentapp.Models.Appointment;
import com.emrekirdim.appointmentapp.Models.AppointmentResult;
import com.emrekirdim.appointmentapp.Models.AppointmentStatus;
import com.emrekirdim.appointmentapp.Models.Doctor;
import com.emrekirdim.appointmentapp.Models.User;
import com.emrekirdim.appointmentapp.Repositories.AppointmentRepository;
import com.emrekirdim.appointmentapp.Repositories.DoctorRepository;
import com.emrekirdim.appointmentapp.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    private Appointment mapToEntity(AppointmentDto dto) {
        Appointment appointment = new Appointment();
        appointment.setId(dto.getId());

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));

        appointment.setUser(user);
        appointment.setDoctor(doctor);
        appointment.setDateTime(dto.getDateTime());
        appointment.setStatus(dto.getStatus() != null ? dto.getStatus() : AppointmentStatus.SCHEDULED);
        appointment.setResult(dto.getResult() != null ? dto.getResult() : AppointmentResult.UNKNOWN);

        return appointment;
    }

    private AppointmentDto mapToDto(Appointment appointment) {
        AppointmentDto dto = new AppointmentDto();
        dto.setId(appointment.getId());
        dto.setUserId(appointment.getUser().getId());
        dto.setDoctorId(appointment.getDoctor().getId());
        dto.setDateTime(appointment.getDateTime());
        dto.setStatus(appointment.getStatus());
        dto.setResult(appointment.getResult());
        return dto;
    }

    public AppointmentDto bookAppointment(AppointmentDto dto) {
        if (dto.getDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot book an appointment in the past.");
        }

        Appointment appointment = mapToEntity(dto);

        Optional<Appointment> existing = appointmentRepository.findByDoctorAndDateTimeAndStatus(
                appointment.getDoctor(),
                appointment.getDateTime(),
                AppointmentStatus.SCHEDULED);

        if (existing.isPresent()) {
            throw new IllegalArgumentException("This time slot is already taken for the selected doctor.");
        }

        Appointment saved = appointmentRepository.save(appointment);
        return mapToDto(saved);
    }

    public void cancelAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);
    }

    public AppointmentDto updateStatus(Long appointmentId, AppointmentStatus status) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.setStatus(status);
        return mapToDto(appointmentRepository.save(appointment));
    }

    public AppointmentDto updateResult(Long appointmentId, AppointmentResult result) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.setResult(result);
        return mapToDto(appointmentRepository.save(appointment));
    }

    public List<AppointmentDto> getAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsByUserAndStatus(FilterRequestDto filter) {
        return appointmentRepository.findByUserIdAndStatus(filter.getUserId(), filter.getStatus())
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsByDoctorAndStatus(FilterRequestDto filter) {
        return appointmentRepository.findByDoctorIdAndStatus(filter.getDoctorId(), filter.getStatus())
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsByDateRangeAndStatus(FilterRequestDto filter) {
        return appointmentRepository.findByDateTimeBetweenAndStatus(filter.getStart(), filter.getEnd(), filter.getStatus())
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsByUserAndResult(FilterRequestDto filter) {
        AppointmentResult resultEnum = AppointmentResult.valueOf(filter.getResult().toUpperCase());
        return appointmentRepository.findByUserIdAndResult(filter.getUserId(), resultEnum)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsByDoctorAndResult(FilterRequestDto filter) {
        AppointmentResult resultEnum = AppointmentResult.valueOf(filter.getResult().toUpperCase());
        return appointmentRepository.findByDoctorIdAndResult(filter.getDoctorId(), resultEnum)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsByDateRangeAndResult(FilterRequestDto filter) {
        AppointmentResult resultEnum = AppointmentResult.valueOf(filter.getResult().toUpperCase());
        return appointmentRepository.findByDateTimeBetweenAndResult(filter.getStart(), filter.getEnd(), resultEnum)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsBySpecialty(Long specialtyId) {
        return appointmentRepository.findByDoctorSpecialtyId(specialtyId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsBySpecialtyAndDateRange(Long specialtyId, LocalDateTime start, LocalDateTime end) {
        return appointmentRepository.findByDoctorSpecialtyIdAndDateTimeBetween(specialtyId, start, end)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsBySpecialtyAndStatus(Long specialtyId, AppointmentStatus status) {
        return appointmentRepository.findByDoctorSpecialtyIdAndStatus(specialtyId, status)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsBySpecialtyAndDateRangeAndStatus(Long specialtyId, LocalDateTime start, LocalDateTime end, AppointmentStatus status) {
        return appointmentRepository.findByDoctorSpecialtyIdAndDateTimeBetweenAndStatus(specialtyId, start, end, status)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsBySpecialtyAndResult(Long specialtyId, String result) {
        AppointmentResult resultEnum = AppointmentResult.valueOf(result.toUpperCase());
        return appointmentRepository.findByDoctorSpecialtyIdAndResult(specialtyId, resultEnum)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsBySpecialtyAndDateRangeAndResult(Long specialtyId, LocalDateTime start, LocalDateTime end, String result) {
        AppointmentResult resultEnum = AppointmentResult.valueOf(result.toUpperCase());
        return appointmentRepository.findByDoctorSpecialtyIdAndDateTimeBetweenAndResult(specialtyId, start, end, resultEnum)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
