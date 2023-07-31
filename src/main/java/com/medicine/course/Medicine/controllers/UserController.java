package com.medicine.course.Medicine.controllers;

import com.medicine.course.Medicine.models.request.UserDto;
import com.medicine.course.Medicine.entities.UserApi;
import com.medicine.course.Medicine.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Log4j2
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @Transactional
    public UserApi registerUser(@RequestBody @Valid UserDto userDto) {
        return userService.registerUser(userDto);
    }
}
