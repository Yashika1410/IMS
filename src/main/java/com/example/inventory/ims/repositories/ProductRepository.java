package com.example.inventory.ims.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.example.inventory.ims.entities.Product;

public interface ProductRepository extends CrudRepository<Product,Long>{

    Optional<Product> findByIdAndIsDeletedIsFalse(Long id);

    Page<Product> findAllByIsDeletedFalse(Pageable pageable);
    
}
