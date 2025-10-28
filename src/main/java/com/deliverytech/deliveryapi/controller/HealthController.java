package com.deliverytech.deliveryapi.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/health")
    public Map<String, String> health(){
        return Map.of(
            "Status", "UP",
            "Timestamp", LocalDateTime.now().toString(),
            "Service", "Delivery API"
        );
    }
}
