package com.example.inventory.ims.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
  @NotNull(message = "Please provide name")
  @NotBlank(message = "Please provide name")
  private String name;
  @Email(message = "Invalid Email")
  private String email;
  @NotNull(message = "Please provide password")
  @NotBlank(message = "Please provide password")
  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Invalid password. It must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, one digit, and one special character.")
  private String password;
  
}
