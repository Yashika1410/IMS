package com.example.inventory.ims.dto.request;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateProductRequest implements Serializable {
    @NotEmpty(message = "Name is required")
    private String name;
    private String description;
    @Default
    private double price=0;
    private int stockQuantity;
}
