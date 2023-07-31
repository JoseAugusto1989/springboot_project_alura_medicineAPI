package com.medicine.course.Medicine.repositories;

import com.medicine.course.Medicine.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
