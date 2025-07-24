package com.emrekirdim.appointmentapp.Repositories;

import com.emrekirdim.appointmentapp.Models.Doctor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findBySpecialtyId(Long specialtyId);
    Optional<Doctor> findByNameAndSurname(String name, String surname);
    boolean existsByTitleAndNameAndSurname(String title, String name, String surname);
}
