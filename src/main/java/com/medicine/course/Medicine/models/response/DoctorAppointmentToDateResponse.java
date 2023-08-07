package com.medicine.course.Medicine.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorAppointmentToDateResponse {

    private String doctorName;
    private String speciality;
    private String patientName;
    private String patientPhone;
}
