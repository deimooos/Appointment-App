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

    private Doctor mapToEntity(DoctorDto dto) {
        Doctor doctor = new Doctor();
        doctor.setId(dto.getId());
        doctor.setName(dto.getName());

        if (dto.getSpecialty() != null) {
            Specialty specialty = new Specialty();
            specialty.setId(dto.getSpecialty().getId());
            specialty.setName(dto.getSpecialty().getName());
            doctor.setSpecialty(specialty);
        } else {
            doctor.setSpecialty(null);
        }
        return doctor;
    }


    private DoctorDto mapToDto(Doctor doctor) {
        DoctorDto dto = new DoctorDto();
        dto.setId(doctor.getId());
        dto.setName(doctor.getName());

        if (doctor.getSpecialty() != null) {
            SpecialtyDto specialtyDto = new SpecialtyDto();
            specialtyDto.setId(doctor.getSpecialty().getId());
            specialtyDto.setName(doctor.getSpecialty().getName());
            dto.setSpecialty(specialtyDto);
        }
        return dto;
    }

    public DoctorDto createDoctor(DoctorDto doctorDto) {
        Doctor doctor = mapToEntity(doctorDto);
        Doctor savedDoctor = doctorRepository.save(doctor);
        return mapToDto(savedDoctor);
    }

    public List<DoctorDto> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public DoctorDto updateDoctor(DoctorDto doctorDto) {
        Doctor doctor = mapToEntity(doctorDto);
        Doctor updatedDoctor = doctorRepository.save(doctor);
        return mapToDto(updatedDoctor);
    }

    public void deleteDoctor(DoctorDto doctorDto) {
        Doctor doctor = mapToEntity(doctorDto);
        doctorRepository.delete(doctor);
    }

    public List<DoctorDto> getDoctorsBySpecialty(Long specialtyId) {
        return doctorRepository.findBySpecialtyId(specialtyId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
