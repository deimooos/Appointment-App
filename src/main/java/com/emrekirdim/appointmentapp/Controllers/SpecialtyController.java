package com.emrekirdim.appointmentapp.Controllers;

import com.emrekirdim.appointmentapp.DTO.SpecialtyDto;
import com.emrekirdim.appointmentapp.Services.SpecialtyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Specialty Controller", description = "Endpoints for managing medical specialties")
@RestController
@RequestMapping("/api/specialties")
public class SpecialtyController {

    @Autowired
    private SpecialtyService specialtyService;

    @Operation(summary = "Create a new specialty", description = "Creates a new specialty using the provided data.")
    @PostMapping("/create")
    public ResponseEntity<String> createSpecialty(@Valid @RequestBody SpecialtyDto specialtyDto) {
        specialtyService.createSpecialty(specialtyDto);
        return ResponseEntity.ok("Specialty created successfully.");
    }

    @Operation(summary = "Update an existing specialty", description = "Updates a specialty based on the provided ID and details.")
    @PutMapping("/update")
    public ResponseEntity<String> updateSpecialty(@Valid @RequestBody SpecialtyDto specialtyDto) {
        specialtyService.updateSpecialty(specialtyDto);
        return ResponseEntity.ok("Specialty updated successfully.");
    }

    @Operation(summary = "Delete a specialty", description = "Deletes a specialty by ID if it exists and has no associated doctors.")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteSpecialty(@RequestBody SpecialtyDto specialtyDto) {
        specialtyService.deleteSpecialty(specialtyDto);
        return ResponseEntity.ok("Specialty deleted successfully.");
    }

    @Operation(summary = "Retrieve all specialties", description = "Returns a list of all specialties stored in the system.")
    @GetMapping("/all")
    public List<SpecialtyDto> getAllSpecialties() {
        return specialtyService.getAllSpecialties();
    }

    @Operation(summary = "Get a specialty by ID", description = "Retrieves a specialty using its ID.")
    @PostMapping("/get-by-id")
    public SpecialtyDto getSpecialtyById(@Valid @RequestBody SpecialtyDto specialtyDto) {
        return specialtyService.getSpecialtyById(specialtyDto.getId());
    }
}
