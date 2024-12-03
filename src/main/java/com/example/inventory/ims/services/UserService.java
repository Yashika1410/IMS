package com.example.inventory.ims.services;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.inventory.ims.dto.request.CreateUserRequest;
import com.example.inventory.ims.entities.Role;
import com.example.inventory.ims.entities.User;
import com.example.inventory.ims.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
     private final UserRepository userRepository;
     private final PasswordEncoder passwordEncoder;

    public User getUserByEmail(String email) {
        return userRepository.findByEmailAndIsDeletedIsFalse(email.toLowerCase())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found by this email" + email));
    }

    public boolean checkUserByEmail(String email) {
       return userRepository.existsByEmailAndIsDeletedIsFalse(email.toLowerCase());
    }

    public User createUser(CreateUserRequest signUpRequest) {
        if(userRepository.existsByEmailAndIsDeletedIsFalse(signUpRequest.getEmail()))
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,"Email already exits");
       User user = User.builder()
                        .email(signUpRequest.getEmail())
                        .name(signUpRequest.getName())
                        .role(Role.USER)
                        .password(passwordEncoder.encode(signUpRequest.getPassword()))
                        .build();
    user.setCreatedAt(LocalDateTime.now());
    user.setUpdatedAt(LocalDateTime.now());
    user.setIsDeleted(false);
    return userRepository.save(user);
    }

}
