package com.medicine.course.Medicine.controllers;

import com.medicine.course.Medicine.models.request.MedicineDto;
import com.medicine.course.Medicine.entities.Medicine;
import com.medicine.course.Medicine.models.response.MedicineResponse;
import com.medicine.course.Medicine.services.MedicineService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/medicine")
@RequiredArgsConstructor
public class MedicineController {

    private final MedicineService medicineService;
    private final DateUtil dateUtil;

    @PostMapping
    public ResponseEntity<Medicine> registerMedicine(@RequestBody @Valid MedicineDto medicineDto) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        medicineService.registerMedicine(medicineDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Medicine>> listAllMedicine() {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        var listMedicine = medicineService.listAllMedicine();
        return ResponseEntity.ok(listMedicine);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicine> findById(@PathVariable Long id) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        var medicineId = medicineService.findById(id);
        return ResponseEntity.ok(medicineId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicineResponse> updateMedicine(@RequestBody @Valid MedicineDto medicineDto,
                                                           @PathVariable Long id) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        MedicineResponse response = medicineService.updateMedicine(id, medicineDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicine(@PathVariable Long id) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        medicineService.deleteMedicine(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/deactivate/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String inactiveMedicine(@PathVariable Long id) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Medicine updated with successfully, id: " + id);
        medicineService.updateMedicineStatus(id, false);
        return "Medicine deactivated successfully";
    }

    @PutMapping("/reactive/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String reactiveMedicine(@PathVariable Long id) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()) + " Medicine updated with successfully, id: " + id);
        medicineService.updateMedicineStatus(id, true);
        return "Medicine reactivated successfully";
    }

    @PutMapping("/decreaseMedicine/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String decreaseMedicineQuantity(@PathVariable Long id, @RequestParam int quantityToDecrease) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        medicineService.decreaseMedicineQuantity(id, quantityToDecrease);
        return "Medicine decreased successfully";
    }

    @PutMapping("/increaseMedicine/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String increaseMedicineQuantity(@PathVariable Long id, @RequestParam int quantityToIncrease) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        medicineService.increaseMedicineQuantity(id, quantityToIncrease);
        return "Medicine increased successfully";
    }

}
