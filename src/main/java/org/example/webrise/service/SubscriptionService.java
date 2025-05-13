package org.example.webrise.service;

import org.example.webrise.dto.CreateSubscriptionRequest;
import org.example.webrise.dto.SubscriptionDto;

import java.util.List;
import java.util.UUID;

public interface SubscriptionService {
    SubscriptionDto addSubscriptionToUser(UUID id, CreateSubscriptionRequest request);

    List<SubscriptionDto> getUserSubscriptions(UUID id);

    void removeSubscriptionFromUser(UUID userId, UUID subscriptionId);

    List<SubscriptionDto> getTopSubscriptions();

}
