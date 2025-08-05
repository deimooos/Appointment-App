package com.emrekirdim.appointmentapp.Services;

import com.emrekirdim.appointmentapp.DTO.SpecialtyDto;
import com.emrekirdim.appointmentapp.Models.Specialty;
import com.emrekirdim.appointmentapp.Repositories.DoctorRepository;
import com.emrekirdim.appointmentapp.Repositories.SpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecialtyService implements BasicGenericService<SpecialtyDto, Long> {

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Autowired
    private DoctorRepository doctorRepository;

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

    @Override
    public SpecialtyDto create(SpecialtyDto dto) throws IllegalArgumentException{
        if (specialtyRepository.findByName(dto.getName().trim()).isPresent()) {
            throw new IllegalArgumentException("A specialty with this name already exists.");
        }
        Specialty saved = specialtyRepository.save(mapToEntity(dto));
        return mapToDto(saved);
    }

    @Override
    public List<SpecialtyDto> getAll() {
        return specialtyRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public SpecialtyDto update(SpecialtyDto dto) throws IllegalArgumentException{
        Specialty specialty = mapToEntity(dto);

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

    @Override
    public void delete(Long id) throws IllegalArgumentException{
        if (id == null) {
            throw new IllegalArgumentException("Specialty id must not be null.");
        }
        Specialty specialty = specialtyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Specialty not found with id: " + id));
        boolean hasDoctors = doctorRepository.existsBySpecialtyId(id);
        if (hasDoctors) {
            throw new IllegalStateException("Cannot delete specialty. First delete doctors under this specialty.");
        }
        specialtyRepository.delete(specialty);
    }

    public SpecialtyDto getById(Long id) throws IllegalArgumentException{
        Specialty specialty = specialtyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Specialty not found with id: " + id));
        return mapToDto(specialty);
    }

    public boolean existsById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Specialty id must not be null.");
        }
        return specialtyRepository.existsById(id);
    }

    public Specialty getEntityById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Specialty id must not be null.");
        }
        return specialtyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Specialty not found with id: " + id));
    }

}
