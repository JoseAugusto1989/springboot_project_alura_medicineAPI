package com.medicine.course.Medicine.controllers;

import com.medicine.course.Medicine.entities.Appointment;
import com.medicine.course.Medicine.models.request.DataAppointment;
import com.medicine.course.Medicine.models.response.DoctorAppointmentToDateResponse;
import com.medicine.course.Medicine.services.AppointmentService;
import com.medicine.course.Medicine.util.DateUtil;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private DateUtil dateUtil;

    @PostMapping
    @Transactional
    public ResponseEntity<String> saveAppointment(@RequestBody @Valid DataAppointment appointment) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        appointmentService.saveAppointment(appointment);
        return ResponseEntity.ok("Appointment register successfully!");
    }

    // TODO: ajustar e entender este cara
    @GetMapping("/doctor-appointments")
    public ResponseEntity<List<DoctorAppointmentToDateResponse>> findDoctorToDate(
            @RequestParam LocalDate date, @RequestParam String doctorName) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        var listAppointment = appointmentService.findDoctorAppointmentsByDate(date, doctorName);
        return ResponseEntity.ok(listAppointment);
    }
}
