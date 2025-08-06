package com.emrekirdim.appointmentapp.Services;

import com.emrekirdim.appointmentapp.DTO.DoctorCreateDto;
import com.emrekirdim.appointmentapp.DTO.DoctorUpdateDto;
import com.emrekirdim.appointmentapp.DTO.DoctorResponseDto;
import com.emrekirdim.appointmentapp.Models.Doctor;
import com.emrekirdim.appointmentapp.Models.Specialty;
import com.emrekirdim.appointmentapp.Repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService implements AdvancedGenericService<DoctorCreateDto, DoctorUpdateDto, DoctorResponseDto, Long> {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private SpecialtyService specialtyService;

    public void checkAnyDoctorExists() throws IllegalArgumentException {
        if (!doctorRepository.existsAnyDoctor()) {
            throw new IllegalArgumentException("No doctors found in the system.");
        }
    }

    private Doctor mapToEntity(DoctorCreateDto dto) throws IllegalArgumentException {
        if (!specialtyService.existsById(dto.getSpecialtyId())) {
            throw new IllegalArgumentException("Specialty not found with id: " + dto.getSpecialtyId());
        }
        Specialty specialty = specialtyService.getEntityById(dto.getSpecialtyId());

        Doctor doctor = new Doctor();
        doctor.setTitle(dto.getTitle());
        doctor.setName(dto.getName());
        doctor.setSurname(dto.getSurname());
        doctor.setSpecialty(specialty);
        return doctor;
    }

    private DoctorResponseDto mapToResponse(Doctor doctor) {
        DoctorResponseDto dto = new DoctorResponseDto();
        dto.setId(doctor.getId());
        dto.setTitle(doctor.getTitle());
        dto.setName(doctor.getName());
        dto.setSurname(doctor.getSurname());
        dto.setSpecialtyId(doctor.getSpecialty().getId());
        return dto;
    }

    @Override
    public DoctorResponseDto create(DoctorCreateDto doctorDto) throws IllegalArgumentException {
        if (doctorRepository.existsByTitleAndNameAndSurname(
                doctorDto.getTitle(),
                doctorDto.getName(),
                doctorDto.getSurname())) {
            throw new IllegalArgumentException("Doctor with this title, name and surname already exists.");
        }

        Doctor doctor = mapToEntity(doctorDto);
        Doctor savedDoctor = doctorRepository.save(doctor);
        return mapToResponse(savedDoctor);
    }

    @Override
    public List<DoctorResponseDto> getAll() {
        checkAnyDoctorExists();
        return doctorRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DoctorResponseDto update(DoctorUpdateDto doctorDto) throws IllegalArgumentException {
        if (doctorDto.getId() == null) {
            throw new IllegalArgumentException("Doctor id must not be null.");
        }

        Doctor existingDoctor = doctorRepository.findById(doctorDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with id: " + doctorDto.getId()));

        if (doctorDto.getTitle() != null) {
            existingDoctor.setTitle(doctorDto.getTitle());
        }
        if (doctorDto.getName() != null) {
            existingDoctor.setName(doctorDto.getName());
        }
        if (doctorDto.getSurname() != null) {
            existingDoctor.setSurname(doctorDto.getSurname());
        }
        if (doctorDto.getSpecialtyId() != null) {
            if (!specialtyService.existsById(doctorDto.getSpecialtyId())) {
                throw new IllegalArgumentException("Specialty not found with id: " + doctorDto.getSpecialtyId());
            }
            Specialty specialty = specialtyService.getEntityById(doctorDto.getSpecialtyId());
            existingDoctor.setSpecialty(specialty);
        }

        boolean existsAnotherDoctor = doctorRepository.existsByTitleAndNameAndSurnameAndIdNot(
                existingDoctor.getTitle(),
                existingDoctor.getName(),
                existingDoctor.getSurname(),
                existingDoctor.getId()
        );

        if (existsAnotherDoctor) {
            throw new IllegalArgumentException("Doctor with this title, name and surname already exists.");
        }

        Doctor savedDoctor = doctorRepository.save(existingDoctor);
        return mapToResponse(savedDoctor);
    }

    @Override
    public void delete(Long id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("Doctor id must not be null.");
        }
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with id: " + id));
        doctorRepository.delete(doctor);
    }

    public List<DoctorResponseDto> getDoctorsBySpecialty(Long specialtyId) throws IllegalArgumentException {
        if (!specialtyService.existsById(specialtyId)) {
            throw new IllegalArgumentException("Selected specialty does not exist.");
        }
        return doctorRepository.findBySpecialtyId(specialtyId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Doctor getEntityById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Doctor id must not be null.");
        }
        return doctorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with id: " + id));
    }

    public boolean existsById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Doctor id must not be null.");
        }
        return doctorRepository.existsById(id);
    }

    public boolean existsAnyDoctor() {
        return doctorRepository.count() > 0;
    }


}
