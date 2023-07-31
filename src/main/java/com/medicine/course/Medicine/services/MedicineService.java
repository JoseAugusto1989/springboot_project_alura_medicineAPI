package com.medicine.course.Medicine.services;

import com.medicine.course.Medicine.models.request.MedicineDto;
import com.medicine.course.Medicine.entities.Medicine;
import com.medicine.course.Medicine.exception.ObjectNotFoundException;
import com.medicine.course.Medicine.models.response.MedicineResponse;
import com.medicine.course.Medicine.repositories.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedicineService {

    private final MedicineRepository medicineRepository;
    private static final Logger logger = LoggerFactory.getLogger(MedicineService.class);

    @Transactional
    public Medicine registerMedicine(MedicineDto medicineDto) {
        try {
            Medicine medicine = new Medicine();
            medicine.setName(medicineDto.getName());
            medicine.setBatch(medicineDto.getBatch());
            medicine.setQuantity(medicineDto.getQuantity());
            medicine.setValidity(medicineDto.getValidity());
            medicine.setWay(medicineDto.getWay());
            medicine.setLaboratory(medicineDto.getLaboratory());
            medicine.setActive(true);

            medicine = medicineRepository.save(medicine);
            return  medicine;
        } catch (ObjectNotFoundException e) {
            logger.error("Error saving medicine: {}", e.getMessage());
            throw e;
        }
    }

    public List<Medicine> listAllMedicine() {
        return medicineRepository.findAll();
    }

    public Medicine findById(Long id) {
        try {
            Medicine medicine = findMedicineById(id);
            return medicine;
        } catch (ObjectNotFoundException e) {
            logger.error("Error to find medicine: {}", e.getMessage());
            throw e;
        }
    }

    @Transactional
    public MedicineResponse updateMedicine(Long id, MedicineDto medicineDto) {
        try {
            Medicine existingMedicine = findMedicineById(id);

            existingMedicine.setName(medicineDto.getName());
            existingMedicine.setBatch(medicineDto.getBatch());
            existingMedicine.setWay(medicineDto.getWay());
            existingMedicine.setValidity(medicineDto.getValidity());
            existingMedicine.setLaboratory(medicineDto.getLaboratory());
            existingMedicine.setQuantity(medicineDto.getQuantity());
            existingMedicine.setActive(true);

            existingMedicine = medicineRepository.save(existingMedicine);
            return new MedicineResponse(
                    "Medicine update succeefully",
                    existingMedicine.getName(),
                    existingMedicine.getLaboratory(),
                    existingMedicine.getActive()
            );

        } catch (ObjectNotFoundException e) {
            logger.error("Error to updating medicine: {}", e.getMessage());
            throw e;
        }
    }

    @Transactional
    public ResponseEntity<String> deleteMedicine(Long id) {
        try {
            Medicine existingMedicine = findMedicineById(id);
            medicineRepository.delete(existingMedicine);
            return ResponseEntity.ok("Medicine deleted successfully");
        } catch (ObjectNotFoundException e) {
            logger.error("Error deleting medicine: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Medicine not found", e);
        }
    }

    public ResponseEntity<String> updateMedicineStatus(Long userId, boolean isActive) {
        Optional<Medicine> optionalMedicine = medicineRepository.findById(userId);

        if (optionalMedicine.isPresent()) {
            Medicine medicine = optionalMedicine.get();
            medicine.setActive(isActive);
            medicineRepository.save(medicine);
            return ResponseEntity.ok("Medicine updated status successfully");
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Medicine not found with ID: " + userId);
    }

    public Medicine decreaseMedicineQuantity(Long id, int quantityToDecrease) {
        try {
            Medicine existingMedicine = findMedicineById(id);
            int currentQuantity = existingMedicine.getQuantity();
            int newQuantity = currentQuantity - quantityToDecrease;

            if (newQuantity < 0) {
                throw new IllegalArgumentException("Cannot decrease quantity below 0.");
            }

            existingMedicine.setQuantity(newQuantity);
            Medicine updatedMedicine = medicineRepository.save(existingMedicine);
            return updatedMedicine;

        } catch (ObjectNotFoundException e) {
            logger.error("Error updating medicine quantity: {}", e.getMessage());
            throw e;
        }
    }

    public Medicine increaseMedicineQuantity(Long id, int quantityToAdd) {
        try {
            Medicine existingMedicine = findMedicineById(id);
            int currentQuantity = existingMedicine.getQuantity();
            int newQuantity = currentQuantity + quantityToAdd;

            existingMedicine.setQuantity(newQuantity);
            Medicine updatedMedicine = medicineRepository.save(existingMedicine);
            return  updatedMedicine;

        } catch (ObjectNotFoundException e) {
            logger.error("Error updating medicine quantity: {}", e.getMessage());
            throw e;
        }
    }

    private Medicine findMedicineById(Long id) {
        return medicineRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Medicine not found", "id"));
    }
}
