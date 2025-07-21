package com.emrekirdim.appointmentapp.Services;

import com.emrekirdim.appointmentapp.Models.Specialty;
import com.emrekirdim.appointmentapp.Repositories.SpecialtyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialtyService {

    private final SpecialtyRepository specialtyRepository;

    public SpecialtyService(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    public Specialty createSpecialty(Specialty specialty) {
        return specialtyRepository.save(specialty);
    }

    public Specialty updateSpecialty(Specialty specialty) {
        Specialty existing = specialtyRepository.findById(specialty.getId())
                .orElseThrow(() -> new RuntimeException("Specialty not found"));
        existing.setName(specialty.getName());
        return specialtyRepository.save(existing);
    }

    public void deleteSpecialty(Long id) {
        specialtyRepository.deleteById(id);
    }

    public List<Specialty> getAllSpecialties() {
        return specialtyRepository.findAll();
    }

    public Specialty getSpecialtyById(Long id) {
        return specialtyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Specialty not found"));
    }
}
