package com.emrekirdim.appointmentapp.Controllers;

import com.emrekirdim.appointmentapp.Models.Appointment;
import com.emrekirdim.appointmentapp.Models.DateRange;
import com.emrekirdim.appointmentapp.Models.AppointmentStatus;
import com.emrekirdim.appointmentapp.Models.AppointmentResult;
import com.emrekirdim.appointmentapp.Models.FilterRequest;
import com.emrekirdim.appointmentapp.Services.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/book")
    public Appointment bookAppointment(@RequestBody Appointment appointment) {
        return appointmentService.bookAppointment(
                appointment.getUser().getId(),
                appointment.getDoctor().getId(),
                appointment.getDateTime()
        );
    }

    @DeleteMapping("/cancel")
    public void cancelAppointment(@RequestBody Appointment appointment) {
        appointmentService.cancelAppointment(appointment.getId());
    }

    @PutMapping("/update-status")
    public Appointment updateStatus(@RequestBody Appointment appointment) {
        return appointmentService.updateStatus(appointment.getId(), appointment.getStatus());
    }

    @GetMapping("/by-user")
    public List<Appointment> getByUser(@RequestBody Appointment appointment) {
        return appointmentService.getAppointmentsByUser(appointment.getUser().getId());
    }

    @GetMapping("/by-doctor")
    public List<Appointment> getByDoctor(@RequestBody Appointment appointment) {
        return appointmentService.getAppointmentsByDoctor(appointment.getDoctor().getId());
    }

    @PostMapping("/by-date-range")
    public List<Appointment> getByDateRange(@RequestBody DateRange range) {
        return appointmentService.getAppointmentsByDateRange(range.getStart(), range.getEnd());
    }

    @PostMapping("/by-user-status")
    public List<Appointment> getByUserAndStatus(@RequestBody FilterRequest filterRequest) {
        return appointmentService.getAppointmentsByUserAndStatus(filterRequest.getUserId(), filterRequest.getStatus());
    }

    @PostMapping("/by-doctor-status")
    public List<Appointment> getByDoctorAndStatus(@RequestBody FilterRequest filterRequest) {
        return appointmentService.getAppointmentsByDoctorAndStatus(filterRequest.getDoctorId(), filterRequest.getStatus());
    }

    @PostMapping("/by-date-range-status")
    public List<Appointment> getByDateRangeAndStatus(@RequestBody FilterRequest filterRequest) {
        return appointmentService.getAppointmentsByDateRangeAndStatus(filterRequest.getStart(), filterRequest.getEnd(), filterRequest.getStatus());
    }

    @PutMapping("/update-result")
    public Appointment updateResult(@RequestBody Appointment appointment) {
        return appointmentService.updateResult(appointment.getId(), appointment.getResult());
    }

    @PostMapping("/by-user-result")
    public List<Appointment> getByUserAndResult(@RequestBody FilterRequest filterRequest) {
        AppointmentResult result = AppointmentResult.valueOf(filterRequest.getResult().toUpperCase());
        return appointmentService.getAppointmentsByUserAndResult(filterRequest.getUserId(), result);
    }


    @PostMapping("/by-doctor-result")
    public List<Appointment> getByDoctorAndResult(@RequestBody FilterRequest filterRequest) {
        AppointmentResult result = AppointmentResult.valueOf(filterRequest.getResult().toUpperCase());
     return appointmentService.getAppointmentsByDoctorAndResult(filterRequest.getDoctorId(), result);
    }

    @PostMapping("/by-date-range-result")
    public List<Appointment> getByDateRangeAndResult(@RequestBody FilterRequest filterRequest) {
        AppointmentResult result = AppointmentResult.valueOf(filterRequest.getResult().toUpperCase());
     return appointmentService.getAppointmentsByDateRangeAndResult(filterRequest.getStart(), filterRequest.getEnd(), result);
    }

}
