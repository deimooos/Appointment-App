package com.emrekirdim.appointmentapp.Services;

import com.emrekirdim.appointmentapp.DTO.SpecialtyDto;
import com.emrekirdim.appointmentapp.Models.Specialty;
import com.emrekirdim.appointmentapp.Repositories.SpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecialtyService {

    @Autowired
    private SpecialtyRepository specialtyRepository;

    private Specialty mapToEntity(SpecialtyDto dto) {
        Specialty specialty = new Specialty();
        specialty.setId(dto.getId());
        specialty.setName(dto.getName());
        return specialty;
    }

    private SpecialtyDto mapToDto(Specialty specialty) {
        SpecialtyDto dto = new SpecialtyDto();
        dto.setId(specialty.getId());
        dto.setName(specialty.getName());
        return dto;
    }

    public SpecialtyDto createSpecialty(SpecialtyDto specialtyDto) {
        Specialty specialty = mapToEntity(specialtyDto);
        Specialty saved = specialtyRepository.save(specialty);
        return mapToDto(saved);
    }

    public SpecialtyDto updateSpecialty(SpecialtyDto specialtyDto) {
        Specialty specialty = mapToEntity(specialtyDto);
        Specialty updated = specialtyRepository.save(specialty);
        return mapToDto(updated);
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
