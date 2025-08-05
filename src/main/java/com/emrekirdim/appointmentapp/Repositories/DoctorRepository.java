package com.emrekirdim.appointmentapp.Repositories;

import com.emrekirdim.appointmentapp.Models.Doctor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM Doctor d")
    boolean existsAnyDoctor();

    List<Doctor> findBySpecialtyId(Long specialtyId);
    boolean existsByTitleAndNameAndSurname(String title, String name, String surname);
    boolean existsBySpecialtyId(Long specialtyId);
    boolean existsByTitleAndNameAndSurnameAndIdNot(String title, String name, String surname, Long id);
}
