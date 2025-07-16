package com.emrekirdim.appointmentapp.Services;

import com.emrekirdim.appointmentapp.Models.Doctor;
import com.emrekirdim.appointmentapp.Repositories.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Doctor updateDoctor(Doctor doctor) {
        Doctor existing = doctorRepository.findById(doctor.getId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        existing.setName(doctor.getName());
        existing.setSpecialty(doctor.getSpecialty());
        return doctorRepository.save(existing);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
}
