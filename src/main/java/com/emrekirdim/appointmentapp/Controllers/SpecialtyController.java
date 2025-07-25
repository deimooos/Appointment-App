package com.emrekirdim.appointmentapp.Controllers;

import com.emrekirdim.appointmentapp.DTO.SpecialtyDto;
import com.emrekirdim.appointmentapp.Services.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/specialties")
public class SpecialtyController {

    @Autowired
    private SpecialtyService specialtyService;

    @PostMapping("/create")
    public ResponseEntity<String> createSpecialty(@Valid @RequestBody SpecialtyDto specialtyDto) {
        specialtyService.createSpecialty(specialtyDto);
        return ResponseEntity.ok("Specialty created successfully.");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateSpecialty(@Valid @RequestBody SpecialtyDto specialtyDto) {
        specialtyService.updateSpecialty(specialtyDto);
        return ResponseEntity.ok("Specialty updated successfully.");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteSpecialty(@Valid @RequestBody SpecialtyDto specialtyDto) {
        specialtyService.deleteSpecialty(specialtyDto.getId());
        return ResponseEntity.ok("Specialty deleted successfully.");
    }

    @GetMapping("/all")
    public List<SpecialtyDto> getAllSpecialties() {
        return specialtyService.getAllSpecialties();
    }

    @PostMapping("/get-by-id")
    public SpecialtyDto getSpecialtyById(@Valid @RequestBody SpecialtyDto specialtyDto) {
        return specialtyService.getSpecialtyById(specialtyDto.getId());
    }
}
