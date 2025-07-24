package com.emrekirdim.appointmentapp.Services;

import com.emrekirdim.appointmentapp.DTO.SpecialtyDto;
import com.emrekirdim.appointmentapp.Models.Specialty;
import com.emrekirdim.appointmentapp.Repositories.SpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SpecialtyService {

    @Autowired
    private SpecialtyRepository specialtyRepository;

    private Specialty mapToEntity(SpecialtyDto dto) {
        Specialty specialty = new Specialty();
        specialty.setId(dto.getId());
        specialty.setName(dto.getName().trim());
        return specialty;
    }

    private SpecialtyDto mapToDto(Specialty specialty) {
        SpecialtyDto dto = new SpecialtyDto();
        dto.setId(specialty.getId());
        dto.setName(specialty.getName());
        return dto;
    }

    public SpecialtyDto createSpecialty(SpecialtyDto dto) {
        Optional<Specialty> existing = specialtyRepository.findByName(dto.getName().trim());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("A specialty with this name already exists.");
        }

        Specialty specialty = mapToEntity(dto);
        Specialty saved = specialtyRepository.save(specialty);
        return mapToDto(saved);
    }

    public SpecialtyDto updateSpecialty(SpecialtyDto specialtyDto) {
        Specialty specialty = mapToEntity(specialtyDto);

        Specialty existingSpecialty = specialtyRepository.findById(specialty.getId())
                .orElseThrow(() -> new IllegalArgumentException("Specialty not found with id: " + specialty.getId()));

        boolean existsName = specialtyRepository.existsByName(specialty.getName())
                && !existingSpecialty.getName().equals(specialty.getName());

        if (existsName) {
            throw new IllegalArgumentException("Specialty with this name already exists.");
        }

        Specialty updatedSpecialty = specialtyRepository.save(specialty);
        return mapToDto(updatedSpecialty);
    }

    public void deleteSpecialty(Long id) {
        specialtyRepository.deleteById(id);
    }

    public List<SpecialtyDto> getAllSpecialties() {
        return specialtyRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public SpecialtyDto getSpecialtyById(Long id) {
        Specialty specialty = specialtyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Specialty not found"));
        return mapToDto(specialty);
    }
}
