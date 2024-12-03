package com.example.inventory.ims.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
  @Email(message = "Please provide a valid email address")
  private String email;
  @NotBlank(message = "Please provide Password")
  private String password;
  
}
