package com.medicine.course.Medicine.template;

import com.medicine.course.Medicine.models.request.UserDto;

public class UserDtoTemplate {

    public static UserDto buildUserDto() {
        return UserDto.builder()
                .cpf("123.456.789.10")
                .login("testJava@email.com")
                .name("Teste java")
                .email("testJava@email.com")
                .password("123EDC456")
                .address(AddressTemplate.buildAddress())
                .build();
    }
}
