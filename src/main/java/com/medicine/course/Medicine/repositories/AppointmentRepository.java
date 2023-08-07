package com.medicine.course.Medicine.repositories;

import com.medicine.course.Medicine.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Boolean existsByDoctorIdAndDate(Long id, LocalDateTime date);

    Boolean existsByPatientIdAndDateBetween(Long id, LocalDateTime firstTime, LocalDateTime lastTime);
}
