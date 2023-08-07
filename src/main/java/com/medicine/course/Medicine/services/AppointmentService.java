package com.medicine.course.Medicine.services;

import com.medicine.course.Medicine.components.AppointmentComponent;
import com.medicine.course.Medicine.entities.Appointment;
import com.medicine.course.Medicine.entities.Doctor;
import com.medicine.course.Medicine.exception.ValidationException;
import com.medicine.course.Medicine.models.request.DataAppointment;
import com.medicine.course.Medicine.models.response.DoctorAppointmentToDateResponse;
import com.medicine.course.Medicine.repositories.AppointmentRepository;
import com.medicine.course.Medicine.repositories.DoctorRepository;
import com.medicine.course.Medicine.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentComponent appointmentComponent;

    public void saveAppointment(DataAppointment data) {
        if (!patientRepository.existsById(data.getPatientId())) {
            throw new ValidationException("Patient Id not found.");
        }

        if (data.getDoctorId() != null && !doctorRepository.existsById(data.getDoctorId())) {
            throw new ValidationException("Doctor id not found.");
        }

        appointmentComponent.validateAdvanceTime(data);
        appointmentComponent.validateDoctorActive(data);
        appointmentComponent.validateOpeningHours(data);
        appointmentComponent.validatePatientActive(data);
        appointmentComponent.validateConsultationWithDoctorAtTheSameTime(data);
        appointmentComponent.validatePatientWithoutAnotherAppointmentOnTheDay(data);

        var patient = patientRepository.getReferenceById(data.getPatientId());
        var doctor = selectDoctor(data);

        if (doctor == null) {
            throw new ValidationException("Doctor do not exists in this date");
        }
        var appointment = new Appointment(null, doctor, patient, data.getDate());

        appointmentRepository.save(appointment);
    }

    public List<DoctorAppointmentToDateResponse> findDoctorAppointmentsByDate(
            LocalDate date, String doctorName) {

        LocalDate endDate = date.plusDays(1);
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = endDate.atTime(LocalTime.MAX);
        return doctorRepository.findDoctorAppointmentsByDate(startOfDay, endOfDay, doctorName);
    }

    private Doctor selectDoctor(DataAppointment data) {
        if (data.getDoctorId() != null) {
            return doctorRepository.getReferenceById(data.getDoctorId());
        }

        if (data.getSpeciality() == null) {
            throw new ValidationException("Mandatory specialty when doctor is not selected!");
        }

        return doctorRepository.findRandomDoctor(data.getSpeciality(), data.getDate());
    }
}
