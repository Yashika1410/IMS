package com.example.inventory.ims.dto.response;

import java.time.LocalDateTime;

import com.example.inventory.ims.entities.Role;
import com.example.inventory.ims.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public static UserResponseDto toResponseDto(User user){
        return new UserResponseDto(user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getCreatedAt(), user.getUpdatedAt());
    }

}
