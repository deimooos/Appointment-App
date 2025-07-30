package com.emrekirdim.appointmentapp.Controllers;

import com.emrekirdim.appointmentapp.DTO.IdRequestDto;
import com.emrekirdim.appointmentapp.DTO.SpecialtyDto;
import com.emrekirdim.appointmentapp.Services.GenericService;
import com.emrekirdim.appointmentapp.Services.SpecialtyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Specialty Controller", description = "Endpoints for managing medical specialties")
@RestController
@RequestMapping("/api/specialties")
public class SpecialtyController extends GenericController<SpecialtyDto, Long> {

    @Autowired
    private SpecialtyService specialtyService;

    @Override
    protected GenericService<SpecialtyDto, Long> getService() {
        return specialtyService;
    }

    @Override
    protected String getCreateMessage() {
        return "Specialty created successfully.";
    }

    @Override
    protected String getUpdateMessage() {
        return "Specialty updated successfully.";
    }

    @Override
    protected String getDeleteMessage() {
        return "Specialty deleted successfully.";
    }

    @Operation(summary = "Get a specialty by ID", description = "Retrieves a specialty using its ID.")
    @PostMapping("/get-by-id")
    public SpecialtyDto getById(@Valid @RequestBody IdRequestDto<Long> request) {
        return specialtyService.getById(request.getId());
    }
}
