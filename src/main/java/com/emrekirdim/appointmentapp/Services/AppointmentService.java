package com.emrekirdim.appointmentapp.Services;

import com.emrekirdim.appointmentapp.Models.*;
import com.emrekirdim.appointmentapp.Repositories.AppointmentRepository;
import com.emrekirdim.appointmentapp.Repositories.DoctorRepository;
import com.emrekirdim.appointmentapp.Repositories.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              UserRepository userRepository,
                              DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
    }

    public Appointment bookAppointment(Long userId, Long doctorId, LocalDateTime dateTime) {
        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot book an appointment in the past.");
        }

        if (dateTime.getMinute() != 0 || dateTime.getSecond() != 0) {
            throw new IllegalArgumentException("Appointments must be booked on the hour.");
        }

        LocalTime time = dateTime.toLocalTime();
        if (time.isBefore(LocalTime.of(8, 0)) || time.isAfter(LocalTime.of(16, 0))) {
            throw new IllegalArgumentException("Appointments are only allowed between 08:00 and 17:00.");
        }

        if (!time.isBefore(LocalTime.of(12, 0)) && time.isBefore(LocalTime.of(13, 0))) {
            throw new IllegalArgumentException("Appointments cannot be booked between 12:00 and 13:00 (lunch break).");
        }

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        Optional<Appointment> existing = appointmentRepository.findByDoctorAndDateTimeAndStatus(
                doctor, dateTime, AppointmentStatus.ACTIVE);

        if (existing.isPresent()) {
            throw new IllegalArgumentException("This time slot is already taken for the selected doctor.");
        }

        Appointment appointment = new Appointment();
        appointment.setUser(user);
        appointment.setDoctor(doctor);
        appointment.setDateTime(dateTime);
        appointment.setStatus(AppointmentStatus.ACTIVE);

        return appointmentRepository.save(appointment);
    }


    public void cancelAppointment(Long id) {
        Appointment a = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        a.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(a);
    }

    public Appointment updateStatus(Long appointmentId, AppointmentStatus status) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.setStatus(status);
        return appointmentRepository.save(appointment);
    }

    public Appointment updateResult(Long appointmentId, AppointmentResult result) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.setResult(result);
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointmentsByUser(Long userId) {
        return appointmentRepository.findByUserId(userId);
    }

    public List<Appointment> getAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    public List<Appointment> getAppointmentsByDateRange(LocalDateTime start, LocalDateTime end) {
        return appointmentRepository.findByDateTimeBetween(start, end);
    }

    public List<Appointment> getAppointmentsByUserAndStatus(Long userId, AppointmentStatus status) {
        return appointmentRepository.findByUserIdAndStatus(userId, status);
    }

    public List<Appointment> getAppointmentsByDoctorAndStatus(Long doctorId, AppointmentStatus status) {
        return appointmentRepository.findByDoctorIdAndStatus(doctorId, status);
    }

    public List<Appointment> getAppointmentsByDateRangeAndStatus(LocalDateTime start, LocalDateTime end, AppointmentStatus status) {
        return appointmentRepository.findByDateTimeBetweenAndStatus(start, end, status);
    }

    public List<Appointment> getAppointmentsByDoctorAndResult(Long doctorId, AppointmentResult result) {
        return appointmentRepository.findByDoctorIdAndResult(doctorId, result);
    }

    public List<Appointment> getAppointmentsByUserAndResult(Long userId, AppointmentResult result) {
        return appointmentRepository.findByUserIdAndResult(userId, result);
    }

    public List<Appointment> getAppointmentsByDateRangeAndResult(LocalDateTime start, LocalDateTime end, AppointmentResult result) {
        return appointmentRepository.findByDateTimeBetweenAndResult(start, end, result);
    }
}
