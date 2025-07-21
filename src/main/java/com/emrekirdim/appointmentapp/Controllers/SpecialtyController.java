package com.emrekirdim.appointmentapp.Controllers;

import com.emrekirdim.appointmentapp.Models.Specialty;
import com.emrekirdim.appointmentapp.Services.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specialties")
public class SpecialtyController {

    @Autowired
    private SpecialtyService specialtyService;

    @PostMapping("/create")
    public Specialty createSpecialty(@RequestBody Specialty specialty) {
        return specialtyService.createSpecialty(specialty);
    }

    @PutMapping("/update")
    public Specialty updateSpecialty(@RequestBody Specialty specialty) {
        return specialtyService.updateSpecialty(specialty);
    }

    @DeleteMapping("/delete")
    public void deleteSpecialty(@RequestBody Specialty specialty) {
        specialtyService.deleteSpecialty(specialty.getId());
    }

    @GetMapping("/all")
    public List<Specialty> getAllSpecialties() {
        return specialtyService.getAllSpecialties();
    }

    @PostMapping("/get-by-id")
    public Specialty getSpecialtyById(@RequestBody Specialty specialty) {
        return specialtyService.getSpecialtyById(specialty.getId());
    }
}
