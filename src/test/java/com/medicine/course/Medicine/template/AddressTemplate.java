package com.medicine.course.Medicine.template;

import com.medicine.course.Medicine.enums.State;
import com.medicine.course.Medicine.models.request.Address;

public class AddressTemplate {

    public static Address buildAddress() {
        return Address.builder()
                .uf(State.RS)
                .city("Porto Alegre")
                .street("Rua João Nunes")
                .district("Centro")
                .number("1234")
                .postalCode("12244-543")
                .complement("Complement")
                .build();
    }
}
