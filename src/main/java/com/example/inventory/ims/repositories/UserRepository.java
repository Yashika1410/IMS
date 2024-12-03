package com.example.inventory.ims.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.inventory.ims.entities.User;

public interface UserRepository extends CrudRepository<User,Long> {

    Optional<User> findByEmailAndIsDeletedIsFalse(String email);

    Boolean existsByEmailAndIsDeletedIsFalse(String lowerCase);
    
}
