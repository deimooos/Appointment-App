package com.emrekirdim.appointmentapp.Services;

import com.emrekirdim.appointmentapp.DTO.DoctorDto;
import com.emrekirdim.appointmentapp.Models.Doctor;
import com.emrekirdim.appointmentapp.Models.Specialty;
import com.emrekirdim.appointmentapp.DTO.SpecialtyDto;
import com.emrekirdim.appointmentapp.Repositories.DoctorRepository;
import com.emrekirdim.appointmentapp.Repositories.SpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private SpecialtyRepository specialtyRepository;

    public void checkAnyDoctorExists() {
        if (!doctorRepository.existsAnyDoctor()) {
            throw new IllegalArgumentException("No doctors found in the system.");
        }
    }

    private Doctor mapToEntity(DoctorDto dto) {
        Specialty specialty = specialtyRepository.findById(dto.getSpecialtyId())
                .orElseThrow(() -> new IllegalArgumentException("Specialty not found"));
        Doctor doctor = new Doctor();
        doctor.setId(dto.getId());
        doctor.setTitle(dto.getTitle());
        doctor.setName(dto.getName());
        doctor.setSurname(dto.getSurname());
        doctor.setSpecialty(specialty);

        return doctor;
    }


    private DoctorDto mapToDto(Doctor doctor) {
        DoctorDto dto = new DoctorDto();
        dto.setId(doctor.getId());
        dto.setTitle(doctor.getTitle());
        dto.setName(doctor.getName());
        dto.setSurname(doctor.getSurname());
        dto.setSpecialtyId(doctor.getSpecialty().getId());

        return dto;
    }

    public DoctorDto createDoctor(DoctorDto doctorDto) {
        doctorRepository.findByNameAndSurname(doctorDto.getName(), doctorDto.getSurname())
                .ifPresent(existing -> {
                    throw new RuntimeException("A doctor with the same name and surname already exists.");
                });
        Doctor doctor = mapToEntity(doctorDto);
        Doctor savedDoctor = doctorRepository.save(doctor);
        return mapToDto(savedDoctor);
    }

    public List<DoctorDto> getAllDoctors() {
        checkAnyDoctorExists();
        return doctorRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public DoctorDto updateDoctor(DoctorDto doctorDto) {
        Doctor doctor = mapToEntity(doctorDto);

        Doctor existingDoctor = doctorRepository.findById(doctor.getId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with id: " + doctor.getId()));

        boolean existsTitleNameSurname = doctorRepository.existsByTitleAndNameAndSurname(
                doctor.getTitle(),
                doctor.getName(),
                doctor.getSurname()
        ) && (
                !existingDoctor.getTitle().equals(doctor.getTitle()) ||
                        !existingDoctor.getName().equals(doctor.getName()) ||
                        !existingDoctor.getSurname().equals(doctor.getSurname())
        );

        if (existsTitleNameSurname) {
            throw new IllegalArgumentException("Doctor with this title, name and surname already exists.");
        }

        Doctor updatedDoctor = doctorRepository.save(doctor);
        return mapToDto(updatedDoctor);
    }
    public void deleteDoctor(DoctorDto doctorDto) {
        Long id = doctorDto.getId();
        if (id == null) {
            throw new IllegalArgumentException("Doctor id must not be null.");
        }
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with id: " + id));
        doctorRepository.delete(doctor);
    }

    public List<DoctorDto> getDoctorsBySpecialty(Long specialtyId) {
        return doctorRepository.findBySpecialtyId(specialtyId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
