package com.medicine.course.Medicine.repositories;

import com.medicine.course.Medicine.entities.Doctor;
import com.medicine.course.Medicine.enums.MedicalSpeciality;
import com.medicine.course.Medicine.models.response.DoctorAppointmentToDateResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Page<Doctor> findAllByActiveTrue(Pageable pageable);

    @Query("""
            select d from Doctor d
            where
            d.active = 1
            and
            d.speciality = :speciality
            and
            d.id not in(
                select a.doctor.id from Appointment a
                where
                a.date = :date
            )
            order by rand()
            limit 1
            """)
    Doctor findRandomDoctor(MedicalSpeciality speciality, LocalDateTime date);

    @Query("""
            select d.active
            from Doctor d
            where
            d.id = :id
            """)
    Boolean findByActiveId(Long id);

    @Query("""        
            select d.name, d.speciality, p.name, p.phone
            from Doctor d
            join Appointment a on d.id = a.doctor.id
            join Patient p on a.patient.id = p.id
            where a.date >= :startDate and a.date < :endDate
            and d.name = :doctorName
            """)
    List<DoctorAppointmentToDateResponse> findDoctorAppointmentsByDate(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("doctorName") String doctorName);

}
