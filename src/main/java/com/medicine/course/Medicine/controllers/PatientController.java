package com.medicine.course.Medicine.controllers;

import com.medicine.course.Medicine.entities.Patient;
import com.medicine.course.Medicine.models.request.PatientDto;
import com.medicine.course.Medicine.models.response.PatientResponse;
import com.medicine.course.Medicine.services.PatientService;
import com.medicine.course.Medicine.util.DateUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final DateUtil dateUtil;

    @PostMapping
    public ResponseEntity<Patient> registerPatient(@RequestBody @Valid PatientDto patientDto) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        patientService.registerPatient(patientDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Patient>> listAllPatient() {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        var listPatient =  patientService.listAllPatient();
        return ResponseEntity.ok(listPatient);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> findById(@PathVariable Long id) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        var patientId = patientService.findById(id);
        return ResponseEntity.ok(patientId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponse> updatePatient(@RequestBody @Valid PatientDto patientDto,
                                                         @PathVariable Long id) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Patient updated with successfully, id: " + id);
        PatientResponse response = patientService.updatePatient(id, patientDto);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Patient deleted with successfully, id: " + id);
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pageablePatient")
    public ResponseEntity<Page<Patient>> listPageablePatient(Pageable pageable) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        var page = patientService.listPageablePatient(pageable);
        return ResponseEntity.ok(page);
    }
}
