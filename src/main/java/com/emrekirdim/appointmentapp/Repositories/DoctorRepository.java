package com.emrekirdim.appointmentapp.Repositories;

import com.emrekirdim.appointmentapp.Models.Doctor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM Doctor d")
    boolean existsAnyDoctor();

    List<Doctor> findBySpecialtyId(Long specialtyId);
    Optional<Doctor> findByNameAndSurname(String name, String surname);
    boolean existsByTitleAndNameAndSurname(String title, String name, String surname);
}
