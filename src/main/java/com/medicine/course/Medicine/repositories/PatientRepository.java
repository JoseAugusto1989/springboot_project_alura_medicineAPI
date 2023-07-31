package com.medicine.course.Medicine.repositories;

import com.medicine.course.Medicine.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
