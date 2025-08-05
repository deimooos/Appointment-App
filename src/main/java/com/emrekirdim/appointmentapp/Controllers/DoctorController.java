package com.emrekirdim.appointmentapp.Controllers;

import com.emrekirdim.appointmentapp.DTO.DoctorCreateDto;
import com.emrekirdim.appointmentapp.DTO.DoctorResponseDto;
import com.emrekirdim.appointmentapp.DTO.DoctorUpdateDto;
import com.emrekirdim.appointmentapp.DTO.FilterRequestDto;
import com.emrekirdim.appointmentapp.Services.AdvancedGenericService;
import com.emrekirdim.appointmentapp.Services.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Admin - Doctor Management", description = "Endpoints for administrators to register, update, and manage doctors. Also includes retrieving doctors by specialty.")
@RestController
@RequestMapping("/api/doctors")
public class DoctorController extends AdvancedGenericController<DoctorCreateDto, DoctorUpdateDto, DoctorResponseDto, Long> {

    @Autowired
    private DoctorService doctorService;

    @Override
    protected AdvancedGenericService<DoctorCreateDto, DoctorUpdateDto, DoctorResponseDto, Long> getService() {
        return doctorService;
    }

    @Override
    protected String getCreateMessage() {
        return "Doctor created successfully.";
    }

    @Override
    protected String getUpdateMessage() {
        return "Doctor updated successfully.";
    }

    @Override
    protected String getDeleteMessage() {
        return "Doctor deleted successfully.";
    }

    @Operation(summary = "Retrieve doctors by specialty", description = "Returns a list of doctors filtered by the specified specialty ID. A valid specialty must exist before adding doctors.")
    @PostMapping("/by-specialty")
    public ResponseEntity<List<DoctorResponseDto>> getDoctorsBySpecialty(@Valid @RequestBody FilterRequestDto filterRequestDto) {
        List<DoctorResponseDto> doctors = doctorService.getDoctorsBySpecialty(filterRequestDto.getSpecialtyId());
        return ResponseEntity.ok(doctors);
    }
}
