package com.medicine.course.Medicine.controllers;

import com.medicine.course.Medicine.entities.Doctor;
import com.medicine.course.Medicine.models.request.DoctorDto;
import com.medicine.course.Medicine.models.request.UpdateDoctor;
import com.medicine.course.Medicine.models.response.DoctorResponse;
import com.medicine.course.Medicine.services.DoctorService;
import com.medicine.course.Medicine.util.DateUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/doctor")
public class DoctorController {

    private final DoctorService doctorService;
    private final DateUtil dateUtil;

    @PostMapping
    public ResponseEntity<Doctor> registerDoctor(@RequestBody @Valid DoctorDto doctorDto) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        doctorService.registerDoctor(doctorDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Doctor>> listAllPatient() {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        var listDoctor = doctorService.listAllDoctor();
        return ResponseEntity.ok(listDoctor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> findById(@PathVariable Long id) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        var doctorId = doctorService.findById(id);
        return ResponseEntity.ok(doctorId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponse> updateDoctor(@RequestBody @Valid UpdateDoctor updateDoctor,
                                                       @PathVariable Long id) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Doctor updated with successfully, id: " + id);
        DoctorResponse response = doctorService.updateDoctor(id, updateDoctor);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Doctor deleted with successfully, id: " + id);
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/deactivate/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String inactiveDoctor(@PathVariable Long id) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Doctor deactivate with successfully, id: " + id);
        doctorService.updateDoctorStatus(id, false);
        return "Doctor deactivate successfully";
    }

    @PutMapping("/reactive/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String reactiveDoctor(@PathVariable Long id) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Doctor reactive with successfully, id: " + id);
        doctorService.updateDoctorStatus(id, true);
        return "Doctor reactive successfully";
    }
}
