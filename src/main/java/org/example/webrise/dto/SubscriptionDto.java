package org.example.webrise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDto {
    private UUID id;
    private String serviceName;
    private LocalDateTime createdAt;
}
