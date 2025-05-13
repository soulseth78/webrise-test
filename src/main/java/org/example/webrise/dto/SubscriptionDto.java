package org.example.webrise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class SubscriptionDto {
    private UUID id;
    private String serviceName;
    private LocalDateTime createdAt;
}
