package com.emrekirdim.appointmentapp.Controllers;

import com.emrekirdim.appointmentapp.DTO.AppointmentDto;
import com.emrekirdim.appointmentapp.DTO.FilterRequestDto;
import com.emrekirdim.appointmentapp.Services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/book")
    public AppointmentDto bookAppointment(@Valid @RequestBody AppointmentDto appointmentDto) {
        return appointmentService.bookAppointment(appointmentDto);
    }

    @DeleteMapping("/cancel")
    public void cancelAppointment(@Valid @RequestBody AppointmentDto appointmentDto) {
        appointmentService.cancelAppointment(appointmentDto.getId());
    }

    @PutMapping("/update-status")
    public AppointmentDto updateStatus(@Valid @RequestBody AppointmentDto appointmentDto) {
        return appointmentService.updateStatus(appointmentDto.getId(), appointmentDto.getStatus());
    }

    @PutMapping("/update-result")
    public AppointmentDto updateResult(@Valid @RequestBody AppointmentDto appointmentDto) {
        return appointmentService.updateResult(appointmentDto.getId(), appointmentDto.getResult());
    }

    @GetMapping("/all")
    public List<AppointmentDto> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @PostMapping("/by-user")
    public List<AppointmentDto> getByUser(@Valid @RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsByUser(filterRequestDto.getUserId());
    }

    @PostMapping("/by-doctor")
    public List<AppointmentDto> getByDoctor(@Valid @RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsByDoctor(filterRequestDto.getDoctorId());
    }

    @PostMapping("/by-date-range")
    public List<AppointmentDto> getByDateRange(@Valid @RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsByDateRange(filterRequestDto.getStart(), filterRequestDto.getEnd());
    }

    @PostMapping("/by-user-status")
    public List<AppointmentDto> getByUserAndStatus(@Valid @RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsByUserAndStatus(filterRequestDto);
    }

    @PostMapping("/by-doctor-status")
    public List<AppointmentDto> getByDoctorAndStatus(@Valid @RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsByDoctorAndStatus(filterRequestDto);
    }

    @PostMapping("/by-date-range-status")
    public List<AppointmentDto> getByDateRangeAndStatus(@Valid @RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsByDateRangeAndStatus(filterRequestDto);
    }

    @PostMapping("/by-user-result")
    public List<AppointmentDto> getByUserAndResult(@Valid @RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsByUserAndResult(filterRequestDto);
    }

    @PostMapping("/by-doctor-result")
    public List<AppointmentDto> getByDoctorAndResult(@Valid @RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsByDoctorAndResult(filterRequestDto);
    }

    @PostMapping("/by-date-range-result")
    public List<AppointmentDto> getByDateRangeAndResult(@Valid @RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsByDateRangeAndResult(filterRequestDto);
    }

    @PostMapping("/by-specialty")
    public List<AppointmentDto> getBySpecialty(@Valid @RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsBySpecialty(filterRequestDto.getSpecialtyId());
    }

    @PostMapping("/by-specialty-date-range")
    public List<AppointmentDto> getBySpecialtyAndDateRange(@Valid @RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsBySpecialtyAndDateRange(
                filterRequestDto.getSpecialtyId(),
                filterRequestDto.getStart(),
                filterRequestDto.getEnd()
        );
    }

    @PostMapping("/by-specialty-status")
    public List<AppointmentDto> getBySpecialtyAndStatus(@Valid @RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsBySpecialtyAndStatus(
                filterRequestDto.getSpecialtyId(),
                filterRequestDto.getStatus()
        );
    }

    @PostMapping("/by-specialty-date-range-status")
    public List<AppointmentDto> getBySpecialtyAndDateRangeAndStatus(@Valid @RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsBySpecialtyAndDateRangeAndStatus(
                filterRequestDto.getSpecialtyId(),
                filterRequestDto.getStart(),
                filterRequestDto.getEnd(),
                filterRequestDto.getStatus()
        );
    }

    @PostMapping("/by-specialty-result")
    public List<AppointmentDto> getBySpecialtyAndResult(@Valid @RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsBySpecialtyAndResult(
                filterRequestDto.getSpecialtyId(),
                filterRequestDto.getResult()
        );
    }

    @PostMapping("/by-specialty-date-range-result")
    public List<AppointmentDto> getBySpecialtyAndDateRangeAndResult(@Valid @RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsBySpecialtyAndDateRangeAndResult(
                filterRequestDto.getSpecialtyId(),
                filterRequestDto.getStart(),
                filterRequestDto.getEnd(),
                filterRequestDto.getResult()
        );
    }
}
