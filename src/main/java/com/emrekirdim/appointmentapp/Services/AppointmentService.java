package com.emrekirdim.appointmentapp.Services;

import com.emrekirdim.appointmentapp.DTO.AppointmentDto;
import com.emrekirdim.appointmentapp.DTO.AppointmentHistoryFilterDto;
import com.emrekirdim.appointmentapp.DTO.FilterRequestDto;
import com.emrekirdim.appointmentapp.Models.Appointment;
import com.emrekirdim.appointmentapp.Models.Enums.AppointmentResult;
import com.emrekirdim.appointmentapp.Models.Enums.AppointmentStatus;
import com.emrekirdim.appointmentapp.Models.Doctor;
import com.emrekirdim.appointmentapp.Models.User;
import com.emrekirdim.appointmentapp.Repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentService implements BasicGenericService<AppointmentDto, Long> {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private DoctorService doctorService;

    private Appointment mapToEntity(AppointmentDto dto) throws IllegalArgumentException{
        Appointment appointment = new Appointment();
        appointment.setId(dto.getId());

        User user = userService.getEntityById(dto.getUserId());
        Doctor doctor = doctorService.getEntityById(dto.getDoctorId());

        appointment.setUser(user);
        appointment.setDoctor(doctor);
        appointment.setDateTime(dto.getDateTime());
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.setResult(AppointmentResult.UNKNOWN);

        return appointment;
    }

    private AppointmentDto mapToDto(Appointment appointment) {
        AppointmentDto dto = new AppointmentDto();
        dto.setId(appointment.getId());
        dto.setUserId(appointment.getUser().getId());
        dto.setDoctorId(appointment.getDoctor().getId());
        dto.setDateTime(appointment.getDateTime());
        dto.setStatus(appointment.getStatus());
        dto.setResult(appointment.getResult());
        return dto;
    }

    @Override
    public AppointmentDto create(AppointmentDto dto) throws IllegalArgumentException{
        if (dto.getUserId() == null || !userService.existsById(dto.getUserId())) {
            throw new IllegalArgumentException("A valid user must be selected before booking an appointment.");
        }

        Doctor doctor = doctorService.getEntityById(dto.getDoctorId());

        if (doctor.getSpecialty() == null) {
            throw new IllegalArgumentException("The selected doctor does not have an assigned specialty. Please choose another doctor.");
        }

        if (dto.getDateTime() == null) {
            throw new IllegalArgumentException("Appointment date and time must be provided.");
        }

        if (dto.getDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("You cannot book an appointment in the past.");
        }

        int hour = dto.getDateTime().getHour();
        int minute = dto.getDateTime().getMinute();

        if (hour < 8 || hour >= 17 || hour == 12 || minute != 0) {
            throw new IllegalArgumentException("Appointments can only be booked on the hour between 08:00-12:00 and 13:00-17:00.");
        }

        Optional<Appointment> existing = appointmentRepository.findByDoctorAndDateTimeAndStatus(
                doctor,
                dto.getDateTime(),
                AppointmentStatus.SCHEDULED
        );

        if (existing.isPresent()) {
            throw new IllegalArgumentException("This time slot is already booked for the selected doctor. Please choose another time.");
        }

        Optional<Appointment> existingUser = appointmentRepository.findByUserIdAndDateTimeAndStatus(
                dto.getUserId(),
                dto.getDateTime(),
                AppointmentStatus.SCHEDULED
        );

        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("You already have an appointment at this time with another doctor. Please choose a different time.");
        }

        Appointment appointment = mapToEntity(dto);
        Appointment saved = appointmentRepository.save(appointment);
        return mapToDto(saved);
    }


    @Override
    public void delete(Long id) throws IllegalArgumentException{
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found with id: " + id));

        if (appointment.getStatus() != AppointmentStatus.SCHEDULED) {
            throw new IllegalArgumentException("Only scheduled appointments can be cancelled.");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);
    }

    @Override
    public AppointmentDto update(AppointmentDto dto){
        Appointment appointment = appointmentRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));

        if (dto.getStatus() == AppointmentStatus.COMPLETED) {
            if (appointment.getStatus() != AppointmentStatus.SCHEDULED) {
                throw new IllegalArgumentException("Only scheduled appointments can be marked as completed.");
            }
            appointment.setStatus(AppointmentStatus.COMPLETED);
        }

        if (dto.getResult() == AppointmentResult.SUCCESSFUL) {
            if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
                throw new IllegalArgumentException("Only completed appointments can have a successful result.");
            }
            appointment.setResult(AppointmentResult.SUCCESSFUL);
        }

        if (dto.getResult() == AppointmentResult.UNSUCCESSFUL) {
            if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
                throw new IllegalArgumentException("Only completed appointments can have an unsuccessful result.");
            }
            appointment.setResult(AppointmentResult.UNSUCCESSFUL);
        }

        Appointment updated = appointmentRepository.save(appointment);
        return mapToDto(updated);
    }

    @Override
    public List<AppointmentDto> getAll() throws IllegalArgumentException{
        if (!userService.existsAnyUser()) {
            throw new IllegalArgumentException("No users found in the system.");
        }
        if (!doctorService.existsAnyDoctor()) {
            throw new IllegalArgumentException("No doctors found in the system.");
        }
        return appointmentRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsByUser(Long userId) throws IllegalArgumentException{
        if (!userService.existsAnyUser()) {
            throw new IllegalArgumentException("No users found in the system.");
        }
        return appointmentRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsByDoctor(Long doctorId) throws IllegalArgumentException{
        if (!doctorService.existsAnyDoctor()) {
            throw new IllegalArgumentException("No doctors found in the system.");
        }
        return appointmentRepository.findByDoctorId(doctorId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsByDateRange(LocalDateTime start, LocalDateTime end) throws IllegalArgumentException{
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end dates must be provided.");
        }
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }

        return appointmentRepository.findByDateTimeBetween(start, end)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsByUserAndStatus(FilterRequestDto filter) throws IllegalArgumentException{
        if (!userService.existsAnyUser()) {
            throw new IllegalArgumentException("No users found in the system.");
        }
        return appointmentRepository.findByUserIdAndStatus(filter.getUserId(), filter.getStatus())
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsByDoctorAndStatus(FilterRequestDto filter) throws IllegalArgumentException{
        if (!doctorService.existsAnyDoctor()) {
            throw new IllegalArgumentException("No doctors found in the system.");
        }
        return appointmentRepository.findByDoctorIdAndStatus(filter.getDoctorId(), filter.getStatus())
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsByDateRangeAndStatus(FilterRequestDto filter) throws IllegalArgumentException{
        LocalDateTime start = filter.getStart();
        LocalDateTime end = filter.getEnd();

        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end dates must be provided.");
        }
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }

        return appointmentRepository.findByDateTimeBetweenAndStatus(start, end, filter.getStatus())
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsByUserAndResult(FilterRequestDto filter) throws IllegalArgumentException{
        if (!userService.existsAnyUser()) {
            throw new IllegalArgumentException("No users found in the system.");
        }
        return appointmentRepository.findByUserIdAndResult(filter.getUserId(), filter.getResult())
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsByDoctorAndResult(FilterRequestDto filter) throws IllegalArgumentException{
        if (!doctorService.existsAnyDoctor()) {
            throw new IllegalArgumentException("No doctors found in the system.");
        }
        return appointmentRepository.findByDoctorIdAndResult(filter.getDoctorId(), filter.getResult())
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsByDateRangeAndResult(FilterRequestDto filter) throws IllegalArgumentException{
        LocalDateTime start = filter.getStart();
        LocalDateTime end = filter.getEnd();

        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end dates must be provided.");
        }
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }

        return appointmentRepository.findByDateTimeBetweenAndResult(start, end, filter.getResult())
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsBySpecialty(Long specialtyId) {
        return appointmentRepository.findByDoctorSpecialtyId(specialtyId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsBySpecialtyAndDateRange(Long specialtyId, LocalDateTime start, LocalDateTime end) throws IllegalArgumentException{
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end dates must be provided.");
        }
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }

        return appointmentRepository.findByDoctorSpecialtyIdAndDateTimeBetween(specialtyId, start, end)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsBySpecialtyAndStatus(Long specialtyId, AppointmentStatus status) {
        return appointmentRepository.findByDoctorSpecialtyIdAndStatus(specialtyId, status)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsBySpecialtyAndDateRangeAndStatus(Long specialtyId, LocalDateTime start, LocalDateTime end, AppointmentStatus status) throws IllegalArgumentException{
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end dates must be provided.");
        }
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }

        return appointmentRepository.findByDoctorSpecialtyIdAndDateTimeBetweenAndStatus(specialtyId, start, end, status)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsBySpecialtyAndResult(Long specialtyId, AppointmentResult result) {
        return appointmentRepository.findByDoctorSpecialtyIdAndResult(specialtyId, result)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsBySpecialtyAndDateRangeAndResult(Long specialtyId, LocalDateTime start, LocalDateTime end, AppointmentResult result) throws IllegalArgumentException{
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end dates must be provided.");
        }
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }

        return appointmentRepository.findByDoctorSpecialtyIdAndDateTimeBetweenAndResult(specialtyId, start, end, result)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsByUserAndDateRange(Long userId, LocalDateTime start, LocalDateTime end) throws IllegalArgumentException{
        if (userId == null || !userService.existsById(userId)) {
            throw new IllegalArgumentException("Valid userId must be provided.");
        }
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end dates must be provided.");
        }
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }

        return appointmentRepository.findByUserIdAndDateTimeBetween(userId, start, end)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsByUserAndDateRangeAndStatus(Long userId, LocalDateTime start, LocalDateTime end, AppointmentStatus status) throws IllegalArgumentException{
        if (userId == null || !userService.existsById(userId)) {
            throw new IllegalArgumentException("Valid userId must be provided.");
        }
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end dates must be provided.");
        }
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }

        return appointmentRepository.findByUserIdAndDateTimeBetweenAndStatus(userId, start, end, status)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsByUserAndDateRangeAndResult(Long userId, LocalDateTime start, LocalDateTime end, AppointmentResult result) throws IllegalArgumentException{
        if (userId == null || !userService.existsById(userId)) {
            throw new IllegalArgumentException("Valid userId must be provided.");
        }
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end dates must be provided.");
        }
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }

        return appointmentRepository.findByUserIdAndDateTimeBetweenAndResult(userId, start, end, result)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsByDoctorAndDateRange(Long doctorId, LocalDateTime start, LocalDateTime end) throws IllegalArgumentException{
        if (doctorId == null || !doctorService.existsById(doctorId)) {
            throw new IllegalArgumentException("Valid doctorId must be provided.");
        }
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end dates must be provided.");
        }
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }

        return appointmentRepository.findByDoctorIdAndDateTimeBetween(doctorId, start, end)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsByDoctorAndDateRangeAndStatus(Long doctorId, LocalDateTime start, LocalDateTime end, AppointmentStatus status) throws IllegalArgumentException{
        if (doctorId == null || !doctorService.existsById(doctorId)) {
            throw new IllegalArgumentException("Valid doctorId must be provided.");
        }
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end dates must be provided.");
        }
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }

        return appointmentRepository.findByDoctorIdAndDateTimeBetweenAndStatus(doctorId, start, end, status)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentDto> getAppointmentsByDoctorAndDateRangeAndResult(Long doctorId, LocalDateTime start, LocalDateTime end, AppointmentResult result) throws IllegalArgumentException{
        if (doctorId == null || !doctorService.existsById(doctorId)) {
            throw new IllegalArgumentException("Valid doctorId must be provided.");
        }
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end dates must be provided.");
        }
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }

        return appointmentRepository.findByDoctorIdAndDateTimeBetweenAndResult(doctorId, start, end, result)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public boolean isDoctorAvailable(Long doctorId, LocalDateTime appointmentDateTime) throws IllegalArgumentException{
        Doctor doctor = doctorService.getEntityById(doctorId);

        int hour = appointmentDateTime.getHour();
        int minute = appointmentDateTime.getMinute();
        if (hour < 8 || hour >= 17) {
            return false;
        }
        if (hour == 12) {
            return false;
        }

        if (minute != 0) {
            return false;
        }

        Optional<Appointment> existing = appointmentRepository.findByDoctorAndDateTimeAndStatus(
                doctor,
                appointmentDateTime,
                AppointmentStatus.SCHEDULED
        );

        return !existing.isPresent();

    }

    public List<LocalDateTime> getAvailableTimeSlots(Long doctorId, LocalDate date) throws IllegalArgumentException{
        Doctor doctor = doctorService.getEntityById(doctorId);

        List<LocalDateTime> availableSlots = new ArrayList<>();

        for (int hour = 8; hour < 17; hour++) {
            if (hour == 12) continue;

            LocalDateTime slot = date.atTime(hour, 0);

            boolean isTaken = appointmentRepository
                    .findByDoctorAndDateTimeAndStatus(
                            doctor,
                            slot,
                            AppointmentStatus.SCHEDULED
                    )
                    .isPresent();

            if (!isTaken && slot.isAfter(LocalDateTime.now())) {
                availableSlots.add(slot);
            }
        }

        if (availableSlots.isEmpty()) {
            throw new IllegalArgumentException("No available time slots for the selected doctor on this date.");
        }

        return availableSlots;
    }

    public List<AppointmentDto> getAppointmentsByUserAndTimeType(AppointmentHistoryFilterDto filter) throws IllegalArgumentException{
        if (filter.getUserId() == null || !userService.existsById(filter.getUserId())) {
            throw new IllegalArgumentException("Valid userId must be provided.");
        }

        LocalDateTime now = LocalDateTime.now();
        List<Appointment> appointments;

        switch (filter.getTimeType()) {
            case PAST:
                appointments = appointmentRepository.findByUserIdAndDateTimeBefore(
                        filter.getUserId(), now);
                break;
            case UPCOMING:
                appointments = appointmentRepository.findByUserIdAndDateTimeAfter(
                        filter.getUserId(), now);
                break;
            case ALL:
            default:
                appointments = appointmentRepository.findByUserId(filter.getUserId());
                break;
        }

        if (filter.getStatus() != null) {
            appointments = appointments.stream()
                    .filter(a -> a.getStatus() == filter.getStatus())
                    .collect(Collectors.toList());
        }

        if (filter.getResult() != null) {
            appointments = appointments.stream()
                    .filter(a -> a.getResult() == filter.getResult())
                    .collect(Collectors.toList());
        }

        return appointments.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }




}
