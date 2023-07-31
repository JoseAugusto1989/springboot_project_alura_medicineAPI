package com.medicine.course.Medicine.controllers;

import com.medicine.course.Medicine.entities.UserApi;
import com.medicine.course.Medicine.models.response.TokenDataJWT;
import com.medicine.course.Medicine.models.request.UserRequest;
import com.medicine.course.Medicine.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity makeLogin(@RequestBody @Valid UserRequest user) {
        try {
            var tokenAuth = new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword());
            var auth = manager.authenticate(tokenAuth);
            var tokenJWT = tokenService.tokenGenerate((UserApi) auth.getPrincipal());
            return ResponseEntity.ok(new TokenDataJWT(tokenJWT));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
