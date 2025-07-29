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
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.setResult(AppointmentResult.UNKNOWN);

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

        if (!userRepository.existsAnyUser()) {
            throw new IllegalArgumentException("No users found in the system.");
        }
        if (!doctorRepository.existsAnyDoctor()) {
            throw new IllegalArgumentException("No doctors found in the system.");
        }

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
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found with id: " + id));

        if (appointment.getStatus() != AppointmentStatus.SCHEDULED) {
            throw new IllegalArgumentException("Only scheduled appointments can be cancelled.");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);
    }

    public AppointmentDto completeAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (appointment.getStatus() != AppointmentStatus.SCHEDULED) {
            throw new IllegalArgumentException("Only scheduled appointments can be marked as completed.");
        }

        appointment.setStatus(AppointmentStatus.COMPLETED);
        Appointment updated = appointmentRepository.save(appointment);
        return mapToDto(updated);
    }

    public AppointmentDto successfulAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
            throw new IllegalArgumentException("Only completed appointments can have a successful result.");
        }

        appointment.setResult(AppointmentResult.SUCCESSFUL);
        Appointment updated = appointmentRepository.save(appointment);
        return mapToDto(updated);
    }

    public AppointmentDto unsuccessfulAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
            throw new IllegalArgumentException("Only completed appointments can have an unsuccessful result.");
        }

        appointment.setResult(AppointmentResult.UNSUCCESSFUL);
        Appointment updated = appointmentRepository.save(appointment);
        return mapToDto(updated);
    }

    public List<AppointmentDto> getAllAppointments() {
        if (!userRepository.existsAnyUser()) {
            throw new IllegalArgumentException("No users found in the system.");
        }
        if (!doctorRepository.existsAnyDoctor()) {
            throw new IllegalArgumentException("No doctors found in the system.");
        }
        return appointmentRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsByUser(Long userId) {
        if (!userRepository.existsAnyUser()) {
            throw new IllegalArgumentException("No users found in the system.");
        }
        return appointmentRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsByDoctor(Long doctorId) {
        if (!doctorRepository.existsAnyDoctor()) {
            throw new IllegalArgumentException("No doctors found in the system.");
        }
        return appointmentRepository.findByDoctorId(doctorId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsByDateRange(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end dates must be provided.");
        }
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }

        return appointmentRepository.findByDateTimeBetween(start, end)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsByUserAndStatus(FilterRequestDto filter) {
        if (!userRepository.existsAnyUser()) {
            throw new IllegalArgumentException("No users found in the system.");
        }
        return appointmentRepository.findByUserIdAndStatus(filter.getUserId(), filter.getStatus())
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsByDoctorAndStatus(FilterRequestDto filter) {
        if (!doctorRepository.existsAnyDoctor()) {
            throw new IllegalArgumentException("No doctors found in the system.");
        }
        return appointmentRepository.findByDoctorIdAndStatus(filter.getDoctorId(), filter.getStatus())
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsByDateRangeAndStatus(FilterRequestDto filter) {
        LocalDateTime start = filter.getStart();
        LocalDateTime end = filter.getEnd();

        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end dates must be provided.");
        }
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }

        return appointmentRepository.findByDateTimeBetweenAndStatus(start, end, filter.getStatus())
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsByUserAndResult(FilterRequestDto filter) {
        if (!userRepository.existsAnyUser()) {
            throw new IllegalArgumentException("No users found in the system.");
        }
        return appointmentRepository.findByUserIdAndResult(filter.getUserId(), filter.getResult())
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsByDoctorAndResult(FilterRequestDto filter) {
        if (!doctorRepository.existsAnyDoctor()) {
            throw new IllegalArgumentException("No doctors found in the system.");
        }
        return appointmentRepository.findByDoctorIdAndResult(filter.getDoctorId(), filter.getResult())
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsByDateRangeAndResult(FilterRequestDto filter) {
        LocalDateTime start = filter.getStart();
        LocalDateTime end = filter.getEnd();

        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end dates must be provided.");
        }
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }

        return appointmentRepository.findByDateTimeBetweenAndResult(start, end, filter.getResult())
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
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end dates must be provided.");
        }
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }

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
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end dates must be provided.");
        }
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }

        return appointmentRepository.findByDoctorSpecialtyIdAndDateTimeBetweenAndStatus(specialtyId, start, end, status)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsBySpecialtyAndResult(Long specialtyId, AppointmentResult result) {
        return appointmentRepository.findByDoctorSpecialtyIdAndResult(specialtyId, result)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsBySpecialtyAndDateRangeAndResult(Long specialtyId, LocalDateTime start, LocalDateTime end, AppointmentResult result) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end dates must be provided.");
        }
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }

        return appointmentRepository.findByDoctorSpecialtyIdAndDateTimeBetweenAndResult(specialtyId, start, end, result)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

}
