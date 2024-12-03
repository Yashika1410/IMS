package com.example.inventory.ims.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "product")
@Builder
public class Product extends BaseEntity implements Serializable {
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private double price;
    private int stockQuantity;
}
