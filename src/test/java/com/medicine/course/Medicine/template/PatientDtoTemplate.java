package com.medicine.course.Medicine.template;

import com.medicine.course.Medicine.models.request.PatientDto;

public class PatientDtoTemplate {

    public static PatientDto buildPatientDto() {
        return PatientDto.builder()
                .name("Jose Augusto")
                .email("joseaugusto_oliveirapins@email.com")
                .phone("(35) 34543-2345")
                .cpf("123.345.567-87")
                .address(AddressTemplate.buildAddress())
                .build();
    }
}
