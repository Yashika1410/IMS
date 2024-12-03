package com.example.inventory.ims.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.example.inventory.ims.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse implements Serializable {
    private Long id;
    private String name;
    private String description;
    private double price;
    private int stockQuantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProductResponse toResponse(Product product){
        return ProductResponse.builder()
                              .createdAt(product.getCreatedAt())
                              .description(product.getDescription())
                              .id(product.getId())
                              .name(product.getName())
                              .price(product.getPrice())
                              .stockQuantity(product.getStockQuantity())
                              .updatedAt(product.getUpdatedAt())
                              .build();
    }
}
