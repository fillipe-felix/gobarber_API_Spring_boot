package com.bobarber.barber.controllers;


import com.bobarber.barber.dto.EmailDTO;
import com.bobarber.barber.security.JWTUtil;
import com.bobarber.barber.security.UserSpringSecurity;
import com.bobarber.barber.services.AuthService;
import com.bobarber.barber.services.UserService;
import com.bobarber.barber.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/refresh_token")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        UserSpringSecurity user = UserService.authenticated();
        String token = jwtUtil.generateToken(user.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/forgot")
    public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDTO) throws ObjectNotFoundException {
        authService.sendNewPassword(objDTO.getEmail());
        return ResponseEntity.noContent().build();
    }
}
