package com.medicine.course.Medicine.repositories;

import com.medicine.course.Medicine.entities.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
}
