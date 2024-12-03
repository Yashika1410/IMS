package com.example.inventory.ims.entities;

import org.hibernate.annotations.ColumnTransformer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "users")
public class User extends BaseEntity{
    private String name;
    @Column(length = 200, unique = true)
    @Email(message = "Please provide a valid email address")
    @ColumnTransformer(write = "LOWER(?)", read = "LOWER(email)")
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String password;
}
