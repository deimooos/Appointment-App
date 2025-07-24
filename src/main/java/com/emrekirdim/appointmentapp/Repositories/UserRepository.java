package com.emrekirdim.appointmentapp.Repositories;


import com.emrekirdim.appointmentapp.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u")
    boolean existsAnyUser();

    boolean existsByNameAndSurname(String name, String surname);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByIdNum(String idNum);

}