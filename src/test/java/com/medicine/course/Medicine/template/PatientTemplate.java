package com.medicine.course.Medicine.template;

import com.medicine.course.Medicine.entities.Patient;

public class PatientTemplate {

    public static Patient buildPatient() {
        return Patient.builder()
                .id(1L)
                .email("joseaugusto_oliveirapins@email.com")
                .phone("(35) 34543-2345")
                .cpf("123.345.567-87")
                .name("Jose Augusto")
                .address(AddressTemplate .buildAddress())
                .build();
    }
}
