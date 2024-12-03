package com.example.inventory.ims.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConstantHelper {
    public static String jwtSecret;

    @Value("${app.backend.jwtSecret}")
    public void setJWTSecret(String value) {
        ConstantHelper.jwtSecret = value;
    }
}
