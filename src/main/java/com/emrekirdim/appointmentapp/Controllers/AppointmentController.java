package com.emrekirdim.appointmentapp.Controllers;

import com.emrekirdim.appointmentapp.DTO.AppointmentDto;
import com.emrekirdim.appointmentapp.DTO.FilterRequestDto;
import com.emrekirdim.appointmentapp.Services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/book")
    public AppointmentDto bookAppointment(@RequestBody AppointmentDto appointmentDto) {
        return appointmentService.bookAppointment(appointmentDto);
    }

    @DeleteMapping("/cancel")
    public void cancelAppointment(@RequestBody AppointmentDto appointmentDto) {
        appointmentService.cancelAppointment(appointmentDto.getId());
    }

    @PutMapping("/update-status")
    public AppointmentDto updateStatus(@RequestBody AppointmentDto appointmentDto) {
        return appointmentService.updateStatus(appointmentDto.getId(), appointmentDto.getStatus());
    }

    @PutMapping("/update-result")
    public AppointmentDto updateResult(@RequestBody AppointmentDto appointmentDto) {
        return appointmentService.updateResult(appointmentDto.getId(), appointmentDto.getResult());
    }

    @GetMapping("/all")
    public List<AppointmentDto> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @PostMapping("/by-user-status")
    public List<AppointmentDto> getByUserAndStatus(@RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsByUserAndStatus(filterRequestDto);
    }

    @PostMapping("/by-doctor-status")
    public List<AppointmentDto> getByDoctorAndStatus(@RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsByDoctorAndStatus(filterRequestDto);
    }

    @PostMapping("/by-date-range-status")
    public List<AppointmentDto> getByDateRangeAndStatus(@RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsByDateRangeAndStatus(filterRequestDto);
    }

    @PostMapping("/by-user-result")
    public List<AppointmentDto> getByUserAndResult(@RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsByUserAndResult(filterRequestDto);
    }

    @PostMapping("/by-doctor-result")
    public List<AppointmentDto> getByDoctorAndResult(@RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsByDoctorAndResult(filterRequestDto);
    }

    @PostMapping("/by-date-range-result")
    public List<AppointmentDto> getByDateRangeAndResult(@RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsByDateRangeAndResult(filterRequestDto);
    }

    @PostMapping("/by-specialty")
    public List<AppointmentDto> getBySpecialty(@RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsBySpecialty(filterRequestDto.getSpecialtyId());
    }

    @PostMapping("/by-specialty-date-range")
    public List<AppointmentDto> getBySpecialtyAndDateRange(@RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsBySpecialtyAndDateRange(
                filterRequestDto.getSpecialtyId(),
                filterRequestDto.getStart(),
                filterRequestDto.getEnd()
        );
    }

    @PostMapping("/by-specialty-status")
    public List<AppointmentDto> getBySpecialtyAndStatus(@RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsBySpecialtyAndStatus(
                filterRequestDto.getSpecialtyId(),
                filterRequestDto.getStatus()
        );
    }

    @PostMapping("/by-specialty-date-range-status")
    public List<AppointmentDto> getBySpecialtyAndDateRangeAndStatus(@RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsBySpecialtyAndDateRangeAndStatus(
                filterRequestDto.getSpecialtyId(),
                filterRequestDto.getStart(),
                filterRequestDto.getEnd(),
                filterRequestDto.getStatus()
        );
    }

    @PostMapping("/by-specialty-result")
    public List<AppointmentDto> getBySpecialtyAndResult(@RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsBySpecialtyAndResult(
                filterRequestDto.getSpecialtyId(),
                filterRequestDto.getResult()
        );
    }

    @PostMapping("/by-specialty-date-range-result")
    public List<AppointmentDto> getBySpecialtyAndDateRangeAndResult(@RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsBySpecialtyAndDateRangeAndResult(
                filterRequestDto.getSpecialtyId(),
                filterRequestDto.getStart(),
                filterRequestDto.getEnd(),
                filterRequestDto.getResult()
        );
    }
}
