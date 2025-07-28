package com.emrekirdim.appointmentapp.Controllers;

import com.emrekirdim.appointmentapp.DTO.DoctorDto;
import com.emrekirdim.appointmentapp.DTO.FilterRequestDto;
import com.emrekirdim.appointmentapp.Services.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Tag(name = "Doctor Controller", description = "Endpoints for managing doctors")
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Operation(summary = "Create a new doctor", description = "Creates a new doctor with the provided information.")
    @PostMapping("/create")
    public ResponseEntity<String> createDoctor(@Valid @RequestBody DoctorDto doctorDto) {
        doctorService.createDoctor(doctorDto);
        return ResponseEntity.ok("Doctor created successfully.");
    }

    @Operation(summary = "Update an existing doctor", description = "Updates doctor details based on provided data.")
    @PutMapping("/update")
    public ResponseEntity<String> updateDoctor(@Valid @RequestBody DoctorDto doctorDto) {
        doctorService.updateDoctor(doctorDto);
        return ResponseEntity.ok("Doctor updated successfully.");
    }

    @Operation(summary = "Delete a doctor", description = "Deletes a doctor using the provided doctor information.")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteDoctor(@RequestBody DoctorDto doctorDto) {
        doctorService.deleteDoctor(doctorDto);
        return ResponseEntity.ok("Doctor deleted successfully.");
    }

    @Operation(summary = "Retrieve all doctors", description = "Returns a list of all doctors.")
    @GetMapping("/all")
    public List<DoctorDto> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @Operation(summary = "Get doctors by specialty", description = "Returns doctors filtered by the given specialty ID.")
    @PostMapping("/by-specialty")
    public List<DoctorDto> getDoctorsBySpecialty(@Valid @RequestBody FilterRequestDto filterRequestDto) {
        return doctorService.getDoctorsBySpecialty(filterRequestDto.getSpecialtyId());
    }

}
