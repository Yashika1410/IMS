package com.example.inventory.ims.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.inventory.ims.dto.request.CreateUserRequest;
import com.example.inventory.ims.dto.request.LoginRequest;
import com.example.inventory.ims.dto.response.AuthResponse;
import com.example.inventory.ims.services.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest requestWrapper) {
       try {
           if(requestWrapper == null) {
               throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"INVALID REQUEST");
           }
           return authService.login(requestWrapper);

        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"User credentials are invalid");
        }
    }

    @PostMapping("/sign-up")
    public AuthResponse signUpRequest(@RequestBody CreateUserRequest createUserRequest) {
        
        return authService.register(createUserRequest);
    }

    @GetMapping("/ping")
    public String getMethodName() {
        return "OK";
    }
    
    
}
