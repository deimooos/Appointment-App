package com.emrekirdim.appointmentapp.Controllers;

import com.emrekirdim.appointmentapp.DTO.DoctorDto;
import com.emrekirdim.appointmentapp.DTO.FilterRequestDto;
import com.emrekirdim.appointmentapp.Services.DoctorService;
import com.emrekirdim.appointmentapp.Services.BasicGenericService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Doctor Controller", description = "Endpoints for managing doctors")
@RestController
@RequestMapping("/api/doctors")
public class DoctorController extends BasicGenericController<DoctorDto, Long> {

    @Autowired
    private DoctorService doctorService;

    @Override
    protected BasicGenericService<DoctorDto, Long> getService() {
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

    @Operation(summary = "Get doctors by specialty", description = "Returns doctors filtered by the given specialty ID.")
    @PostMapping("/by-specialty")
    public List<DoctorDto> getDoctorsBySpecialty(@Valid @RequestBody FilterRequestDto filterRequestDto) {
        return doctorService.getDoctorsBySpecialty(filterRequestDto.getSpecialtyId());
    }

}
