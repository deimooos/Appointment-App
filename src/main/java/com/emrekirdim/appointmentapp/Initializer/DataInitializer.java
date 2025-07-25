package com.emrekirdim.appointmentapp.Initializer;

import com.emrekirdim.appointmentapp.Models.Doctor;
import com.emrekirdim.appointmentapp.Models.Specialty;
import com.emrekirdim.appointmentapp.Models.User;
import com.emrekirdim.appointmentapp.Repositories.DoctorRepository;
import com.emrekirdim.appointmentapp.Repositories.SpecialtyRepository;
import com.emrekirdim.appointmentapp.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public void run(String... args) throws Exception {

        if (!userRepository.existsAnyUser()) {
            User user1 = new User(null, "Emre", "Kırdım", "emre@example.com", "05362402898", "37297530286");
            User user2 = new User(null, "Hasan", "Kum", "hasan@example.com", "05511182790", "31631029016");
            userRepository.save(user1);
            userRepository.save(user2);
        }

        Specialty cardiology = specialtyRepository.findByName("Cardiology").orElseGet(() -> {
            Specialty s = new Specialty(null, "Cardiology");
            return specialtyRepository.save(s);
        });

        Specialty dermatology = specialtyRepository.findByName("Dermatology").orElseGet(() -> {
            Specialty s = new Specialty(null, "Dermatology");
            return specialtyRepository.save(s);
        });

        if (!doctorRepository.existsAnyDoctor()) {
            Doctor doctor1 = new Doctor(null, "Uzm. Dr.", "Kevser", "Katırcıoğlu", cardiology);
            Doctor doctor2 = new Doctor(null, "Op. Dr.", "Adnan", "Abasıyanık", dermatology);
            doctorRepository.save(doctor1);
            doctorRepository.save(doctor2);
        }

        System.out.println("Initial data loaded.");
    }
}
