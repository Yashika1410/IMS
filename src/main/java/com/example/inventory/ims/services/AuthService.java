package com.example.inventory.ims.services;


import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.inventory.ims.dto.request.CreateUserRequest;
import com.example.inventory.ims.dto.request.LoginRequest;
import com.example.inventory.ims.dto.response.AuthResponse;
import com.example.inventory.ims.dto.response.TokenResponseDto;
import com.example.inventory.ims.dto.response.UserResponseDto;
import com.example.inventory.ims.entities.User;
import com.example.inventory.ims.utils.JwtUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthResponse login(LoginRequest loginRequest) {
        if (loginRequest == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Request");
        }


        User user = userService.getUserByEmail(loginRequest.getEmail());

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Password is not valid");
        }

        TokenResponseDto tokenResponseDto = generateAccessToken(user);
        return AuthResponse
                .builder()
                .user(UserResponseDto.toResponseDto(user))
                .accessToken(tokenResponseDto.getAccessToken())
                .email(tokenResponseDto.getEmail())
                .build();
    }

    public AuthResponse register(CreateUserRequest signUpRequest) {
        if (signUpRequest == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Request");
        }

        if(userService.checkUserByEmail(signUpRequest.getEmail())) {
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User already exists by this email");
        }
        User userResponseDto = userService.createUser(
                signUpRequest);
        return AuthResponse.builder().email(userResponseDto.getEmail()).accessToken(generateAccessToken(userResponseDto).getAccessToken()).build();
    }
    private TokenResponseDto generateAccessToken(User user) {
        String accessToken = jwtUtils.generateJwtToken(user);
        TokenResponseDto token = new TokenResponseDto(accessToken, user.getEmail());
        return token;
    }
}
