package com.emrekirdim.appointmentapp.Repositories;


import com.emrekirdim.appointmentapp.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}