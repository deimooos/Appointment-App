package com.emrekirdim.appointmentapp.Repositories;

import com.emrekirdim.appointmentapp.Models.Appointment;
import com.emrekirdim.appointmentapp.Models.Doctor;
import com.emrekirdim.appointmentapp.Models.User;
import com.emrekirdim.appointmentapp.Models.AppointmentStatus;
import com.emrekirdim.appointmentapp.Models.AppointmentResult;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findByDoctorAndDateTimeAndStatus(Doctor doctor, LocalDateTime dateTime, AppointmentStatus status);
    List<Appointment> findByUserId(Long userId);
    List<Appointment> findByDoctorId(Long doctorId);
    List<Appointment> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Appointment> findByUserIdAndStatus(Long userId, AppointmentStatus status);
    List<Appointment> findByDoctorIdAndStatus(Long doctorId, AppointmentStatus status);
    List<Appointment> findByDateTimeBetweenAndStatus(LocalDateTime start, LocalDateTime end, AppointmentStatus status);
    List<Appointment> findByDoctorIdAndResult(Long doctorId, AppointmentResult result);
    List<Appointment> findByUserIdAndResult(Long userId, AppointmentResult result);
    List<Appointment> findByDateTimeBetweenAndResult(LocalDateTime start, LocalDateTime end, AppointmentResult result);
}
