package com.medicine.course.Medicine.models.request;

import com.medicine.course.Medicine.enums.MedicalSpeciality;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataAppointment {

    private Long doctorId;

    private Long patientId;

    private MedicalSpeciality speciality;

    @NotNull
    @Future
    private LocalDateTime date;
}
