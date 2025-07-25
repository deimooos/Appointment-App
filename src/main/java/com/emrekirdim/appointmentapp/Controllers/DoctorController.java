package com.emrekirdim.appointmentapp.Controllers;

import com.emrekirdim.appointmentapp.DTO.DoctorDto;
import com.emrekirdim.appointmentapp.DTO.FilterRequestDto;
import com.emrekirdim.appointmentapp.Services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/create")
    public ResponseEntity<String> createDoctor(@Valid @RequestBody DoctorDto doctorDto) {
        doctorService.createDoctor(doctorDto);
        return ResponseEntity.ok("Doctor created successfully.");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateDoctor(@Valid @RequestBody DoctorDto doctorDto) {
        doctorService.updateDoctor(doctorDto);
        return ResponseEntity.ok("Doctor updated successfully.");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteDoctor(@Valid @RequestBody DoctorDto doctorDto) {
        doctorService.deleteDoctor(doctorDto);
        return ResponseEntity.ok("Doctor deleted successfully.");
    }

    @GetMapping("/all")
    public List<DoctorDto> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @PostMapping("/by-specialty")
    public List<DoctorDto> getDoctorsBySpecialty(@Valid @RequestBody FilterRequestDto filterRequestDto) {
        return doctorService.getDoctorsBySpecialty(filterRequestDto.getSpecialtyId());
    }

}
