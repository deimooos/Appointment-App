package com.emrekirdim.appointmentapp.Controllers;

import com.emrekirdim.appointmentapp.DTO.SpecialtyDto;
import com.emrekirdim.appointmentapp.Services.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/specialties")
public class SpecialtyController {

    @Autowired
    private SpecialtyService specialtyService;

    @PostMapping("/create")
    public SpecialtyDto createSpecialty(@Valid @RequestBody SpecialtyDto specialtyDto) {
        return specialtyService.createSpecialty(specialtyDto);
    }

    @PutMapping("/update")
    public SpecialtyDto updateSpecialty(@Valid @RequestBody SpecialtyDto specialtyDto) {
        return specialtyService.updateSpecialty(specialtyDto);
    }

    @DeleteMapping("/delete")
    public void deleteSpecialty(@Valid @RequestBody SpecialtyDto specialtyDto) {
        specialtyService.deleteSpecialty(specialtyDto.getId());
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
