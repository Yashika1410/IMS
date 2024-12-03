package com.example.inventory.ims.services;


import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.inventory.ims.entities.User;
import com.example.inventory.ims.repositories.UserRepository;
import com.example.inventory.ims.utils.UserDetailsImpl;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByEmailAndIsDeletedIsFalse(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));
            return UserDetailsImpl.build(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid email is provided");
        }
    }

}
