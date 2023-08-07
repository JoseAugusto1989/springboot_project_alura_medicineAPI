package com.medicine.course.Medicine.template;

import com.medicine.course.Medicine.entities.UserApi;

public class UserApiTemplate {

    public static UserApi buildUserApi() {
        return UserApi.builder()
                .cpf("123.456.789.10")
                .login("testJava@email.com")
                .name("Teste java")
                .email("testJava@email.com")
                .password("123EDC456")
                .active(true)
                .address(AddressTemplate.buildAddress())
                .build();
    }
}
