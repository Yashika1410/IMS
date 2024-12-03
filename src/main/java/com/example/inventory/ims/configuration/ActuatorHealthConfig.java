package com.example.inventory.ims.configuration;

import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class ActuatorHealthConfig implements HealthIndicator {
    private final RestTemplate restTemplate = new RestTemplate();
    @Override
    public Health health() {
        boolean isHealthy = checkYourServiceHealth(); // Replace with actual health check logic
        if (isHealthy) {
            return Health.up().withDetail("HealthCheck", "All good").build();
        }
        return Health.down().withDetail("HealthCheck", "Something went wrong").build();
    }

    private boolean checkYourServiceHealth() {
        try {
            String response = restTemplate.getForObject("http://localhost:8080/api/v1/auth/ping", String.class);

            return response.equals("OK");
        } catch (RestClientException e) {
            e.printStackTrace();
            return false;
        }
    }
}
