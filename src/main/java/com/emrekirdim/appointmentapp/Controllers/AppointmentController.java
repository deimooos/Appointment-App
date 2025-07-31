package com.emrekirdim.appointmentapp.Controllers;

import com.emrekirdim.appointmentapp.DTO.*;
import com.emrekirdim.appointmentapp.Services.AppointmentService;
import com.emrekirdim.appointmentapp.Services.BasicGenericService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Appointment Controller", description = "Endpoints for managing appointments")
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController extends BasicGenericController<AppointmentDto, Long> {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Override
    protected BasicGenericService<AppointmentDto, Long> getService() {
        return appointmentService;
    }

    @Override
    protected String getCreateMessage() {
        return "Appointment booked successfully.";
    }

    @Override
    protected String getUpdateMessage() {
        return "Appointment updated successfully.";
    }

    @Override
    protected String getDeleteMessage() {
        return "Appointment cancelled successfully.";
    }

    @Operation(summary = "Mark an appointment as completed")
    @PutMapping("/complete")
    public ResponseEntity<String> completeAppointment(@Valid @RequestBody IdRequestDto<Long> request) {
        AppointmentDto dto = new AppointmentDto();
        dto.setId(request.getId());
        dto.setStatus(com.emrekirdim.appointmentapp.Models.AppointmentStatus.COMPLETED);
        appointmentService.update(dto);
        return ResponseEntity.ok("Appointment marked as completed.");
    }

    @Operation(summary = "Mark appointment result as successful")
    @PutMapping("/successful")
    public ResponseEntity<String> markAsSuccessful(@Valid @RequestBody IdRequestDto<Long> request) {
        AppointmentDto dto = new AppointmentDto();
        dto.setId(request.getId());
        dto.setResult(com.emrekirdim.appointmentapp.Models.AppointmentResult.SUCCESSFUL);
        appointmentService.update(dto);
        return ResponseEntity.ok("Appointment result marked as successful.");
    }

    @Operation(summary = "Mark appointment result as unsuccessful")
    @PutMapping("/unsuccessful")
    public ResponseEntity<String> markAsUnsuccessful(@Valid @RequestBody IdRequestDto<Long> request) {
        AppointmentDto dto = new AppointmentDto();
        dto.setId(request.getId());
        dto.setResult(com.emrekirdim.appointmentapp.Models.AppointmentResult.UNSUCCESSFUL);
        appointmentService.update(dto);
        return ResponseEntity.ok("Appointment result marked as unsuccessful.");
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

    @Operation(summary = "Check doctor's availability", description = "Checks if the doctor is available at the specified date and time.")
    @PostMapping("/check-availability")
    public ResponseEntity<String> checkDoctorAvailability(@Valid @RequestBody DoctorAvailabilityRequestDto request) {
        boolean available = appointmentService.isDoctorAvailable(request.getDoctorId(), request.getDateTime());

        if (available) {
            return ResponseEntity.ok("The doctor is available at the selected time.");
        } else {
            return ResponseEntity.badRequest().body("The doctor is not available at the selected time.");
        }
    }

    @Operation(
            summary = "Get doctor's daily availability",
            description = "Returns a list of available appointment times for a specific doctor on a given date."
    )
    @PostMapping("/daily-availability")
    public ResponseEntity<List<LocalDateTime>> getDoctorDailyAvailability(
            @Valid @RequestBody DoctorDailyAvailabilityRequestDto requestDto) {
        List<LocalDateTime> availableSlots =
                appointmentService.getAvailableTimeSlots(requestDto.getDoctorId(), requestDto.getDate());
        return ResponseEntity.ok(availableSlots);
    }


}
