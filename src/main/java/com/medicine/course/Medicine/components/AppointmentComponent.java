package com.medicine.course.Medicine.components;

import com.medicine.course.Medicine.exception.ValidationException;
import com.medicine.course.Medicine.models.request.DataAppointment;
import com.medicine.course.Medicine.repositories.AppointmentRepository;
import com.medicine.course.Medicine.repositories.DoctorRepository;
import com.medicine.course.Medicine.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AppointmentComponent {

    private final DoctorRepository doctorRepository;

    private final AppointmentRepository appointmentRepository;

    private final PatientRepository patientRepository;
    private static final int CLINIC_OPENING_HOUR = 7;
    private static final int CLINIC_CLOSING_HOUR = 18;
    private static final int MINUTES_IN_ADVANCE = 30;

    public void validateOpeningHours(DataAppointment data) {
        var dataAppointment = data.getDate();

        var isSunday = dataAppointment.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var beforeOpening = dataAppointment.getHour() < CLINIC_OPENING_HOUR;
        var afterClosing = dataAppointment.getHour() > CLINIC_CLOSING_HOUR;

        if (isSunday || beforeOpening || afterClosing) {
            throw new ValidationException("Consultation outside clinic opening hours.");
        }
    }

    public void validateAdvanceTime(DataAppointment data) {
        var dataAppointment = data.getDate();
        var now = LocalDateTime.now();
        var differenceMinutes = Duration.between(now, dataAppointment).toMinutes();

        if (differenceMinutes < MINUTES_IN_ADVANCE) {
            throw new ValidationException("Consultation must be scheduled 30 minutes in advance.");
        }
    }

    public void validateDoctorActive(DataAppointment data) {
        if (data.getDoctorId() == null) {
            return;
        }

        var activeDoctor = doctorRepository.findByActiveId(data.getDoctorId());
        if (!activeDoctor) {
            throw new ValidationException("Consultation cannot be scheduled with excluded doctor.");
        }
    }

    public void validateConsultationWithDoctorAtTheSameTime(DataAppointment data) {
        var doctorHasOtherConsultationAtTheSameTime = appointmentRepository.existsByDoctorIdAndDate(data.getDoctorId(), data.getDate());

        if (doctorHasOtherConsultationAtTheSameTime) {
            throw new ValidationException("Doctor already has an appointment at the same time.");
        }
    }

    public void validatePatientWithoutAnotherAppointmentOnTheDay(DataAppointment data) {
        var firstTime = data.getDate().withHour(CLINIC_OPENING_HOUR);
        var lastTime = data.getDate().withHour(CLINIC_CLOSING_HOUR);
        var patientHasOtherConsultationOnTheDay = appointmentRepository.existsByPatientIdAndDateBetween(data.getPatientId(), firstTime, lastTime);

        if (patientHasOtherConsultationOnTheDay) {
            throw new ValidationException("Patient already has an appointment scheduled on this day.");
        }
    }

    public void validatePatientActive(DataAppointment data) {
        var activePatient = patientRepository.findActiveById(data.getPatientId());
        if (!activePatient) {
            throw new ValidationException("Consultation cannot be scheduled with excluded patient.");
        }
    }
}
