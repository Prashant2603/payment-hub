package com.example.payment_hub.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class PaymentServiceHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        try {
            // Add your health check logic here
            return Health.up()
                    .withDetail("service", "Payment Service")
                    .withDetail("status", "Running")
                    .build();
        } catch (Exception e) {
            return Health.down()
                    .withDetail("error", e.getMessage())
                    .build();
        }
    }
}