package com.emrekirdim.appointmentapp.Controllers;

import com.emrekirdim.appointmentapp.DTO.AppointmentDto;
import com.emrekirdim.appointmentapp.DTO.FilterRequestDto;
import com.emrekirdim.appointmentapp.Services.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Appointment Controller", description = "Endpoints for managing appointments")
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Operation(summary = "Book an appointment", description = "Creates a new appointment booking with provided details.")
    @PostMapping("/book")
    public ResponseEntity<String> bookAppointment(@Valid @RequestBody AppointmentDto appointmentDto) {
        appointmentService.bookAppointment(appointmentDto);
        return ResponseEntity.ok("Appointment booked successfully.");
    }

    @Operation(summary = "Cancel an appointment", description = "Cancels an existing appointment by its ID.")
    @DeleteMapping("/cancel")
    public ResponseEntity<String> cancelAppointment(@RequestBody AppointmentDto appointmentDto) {
        appointmentService.cancelAppointment(appointmentDto.getId());
        return ResponseEntity.ok("Appointment cancelled successfully.");
    }

    @Operation(summary = "Update appointment status", description = "Updates the status of an appointment.")
    @PutMapping("/update-status")
    public ResponseEntity<String> updateStatus(@Valid @RequestBody AppointmentDto appointmentDto) {
        appointmentService.updateStatus(appointmentDto.getId(), appointmentDto.getStatus());
        return ResponseEntity.ok("Appointment status updated successfully.");
    }

    @Operation(summary = "Update appointment result", description = "Updates the result of an appointment.")
    @PutMapping("/update-result")
    public ResponseEntity<String> updateResult(@Valid @RequestBody AppointmentDto appointmentDto) {
        appointmentService.updateResult(appointmentDto.getId(), appointmentDto.getResult());
        return ResponseEntity.ok("Appointment result updated successfully.");
    }

    @Operation(summary = "Retrieve all appointments", description = "Returns a list of all appointments.")
    @GetMapping("/all")
    public List<AppointmentDto> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @Operation(summary = "Get appointments by user", description = "Returns appointments filtered by user ID.")
    @PostMapping("/by-user")
    public List<AppointmentDto> getByUser(@Valid @RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsByUser(filterRequestDto.getUserId());
    }

    @Operation(summary = "Get appointments by doctor", description = "Returns appointments filtered by doctor ID.")
    @PostMapping("/by-doctor")
    public List<AppointmentDto> getByDoctor(@Valid @RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsByDoctor(filterRequestDto.getDoctorId());
    }

    @Operation(summary = "Get appointments by date range", description = "Returns appointments within a specified date range.")
    @PostMapping("/by-date-range")
    public List<AppointmentDto> getByDateRange(@Valid @RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsByDateRange(filterRequestDto.getStart(), filterRequestDto.getEnd());
    }

    @Operation(summary = "Get appointments by user and status", description = "Returns appointments filtered by user ID and status.")
    @PostMapping("/by-user-status")
    public List<AppointmentDto> getByUserAndStatus(@Valid @RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsByUserAndStatus(filterRequestDto);
    }

    @Operation(summary = "Get appointments by doctor and status", description = "Returns appointments filtered by doctor ID and status.")
    @PostMapping("/by-doctor-status")
    public List<AppointmentDto> getByDoctorAndStatus(@Valid @RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsByDoctorAndStatus(filterRequestDto);
    }

    @Operation(summary = "Get appointments by date range and status", description = "Returns appointments filtered by date range and status.")
    @PostMapping("/by-date-range-status")
    public List<AppointmentDto> getByDateRangeAndStatus(@Valid @RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsByDateRangeAndStatus(filterRequestDto);
    }

    @Operation(summary = "Get appointments by user and result", description = "Returns appointments filtered by user ID and result.")
    @PostMapping("/by-user-result")
    public List<AppointmentDto> getByUserAndResult(@Valid @RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsByUserAndResult(filterRequestDto);
    }

    @Operation(summary = "Get appointments by doctor and result", description = "Returns appointments filtered by doctor ID and result.")
    @PostMapping("/by-doctor-result")
    public List<AppointmentDto> getByDoctorAndResult(@Valid @RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsByDoctorAndResult(filterRequestDto);
    }

    @Operation(summary = "Get appointments by date range and result", description = "Returns appointments filtered by date range and result.")
    @PostMapping("/by-date-range-result")
    public List<AppointmentDto> getByDateRangeAndResult(@Valid @RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsByDateRangeAndResult(filterRequestDto);
    }

    @Operation(summary = "Get appointments by specialty", description = "Returns appointments filtered by specialty ID.")
    @PostMapping("/by-specialty")
    public List<AppointmentDto> getBySpecialty(@Valid @RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsBySpecialty(filterRequestDto.getSpecialtyId());
    }

    @Operation(summary = "Get appointments by specialty and date range", description = "Returns appointments filtered by specialty and date range.")
    @PostMapping("/by-specialty-date-range")
    public List<AppointmentDto> getBySpecialtyAndDateRange(@Valid @RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsBySpecialtyAndDateRange(
                filterRequestDto.getSpecialtyId(),
                filterRequestDto.getStart(),
                filterRequestDto.getEnd()
        );
    }

    @Operation(summary = "Get appointments by specialty and status", description = "Returns appointments filtered by specialty and status.")
    @PostMapping("/by-specialty-status")
    public List<AppointmentDto> getBySpecialtyAndStatus(@Valid @RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsBySpecialtyAndStatus(
                filterRequestDto.getSpecialtyId(),
                filterRequestDto.getStatus()
        );
    }

    @Operation(summary = "Get appointments by specialty, date range and status", description = "Returns appointments filtered by specialty, date range and status.")
    @PostMapping("/by-specialty-date-range-status")
    public List<AppointmentDto> getBySpecialtyAndDateRangeAndStatus(@Valid @RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsBySpecialtyAndDateRangeAndStatus(
                filterRequestDto.getSpecialtyId(),
                filterRequestDto.getStart(),
                filterRequestDto.getEnd(),
                filterRequestDto.getStatus()
        );
    }

    @Operation(summary = "Get appointments by specialty and result", description = "Returns appointments filtered by specialty and result.")
    @PostMapping("/by-specialty-result")
    public List<AppointmentDto> getBySpecialtyAndResult(@Valid @RequestBody FilterRequestDto filterRequestDto) {
        return appointmentService.getAppointmentsBySpecialtyAndResult(
                filterRequestDto.getSpecialtyId(),
                filterRequestDto.getResult()
        );
    }

    @Operation(summary = "Get appointments by specialty, date range and result", description = "Returns appointments filtered by specialty, date range and result.")
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
