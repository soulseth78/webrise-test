package org.example.webrise.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.webrise.dto.CreateSubscriptionRequest;
import org.example.webrise.dto.SubscriptionDto;
import org.example.webrise.service.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/users/{userId}/subscriptions")
@RequiredArgsConstructor
public class UserSubscriptionsController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<SubscriptionDto> addSubscriptionToUser(@PathVariable UUID userId,
                                                                 @RequestBody CreateSubscriptionRequest request) {
        log.info("Добавление подписки пользователю с ID: {}", userId);
        SubscriptionDto subscription = subscriptionService.addSubscriptionToUser(userId, request);
        return ResponseEntity.ok(subscription);
    }

    @GetMapping
    public  ResponseEntity<List<SubscriptionDto>> getUserSubscriptions(@PathVariable UUID userId) {
        log.info("Получение подписок пользователя с ID: {}", userId);
        List<SubscriptionDto> subscriptions = subscriptionService.getUserSubscriptions(userId);
        return ResponseEntity.ok(subscriptions);
    }

    @DeleteMapping("/{sub_id}")
    public ResponseEntity<Void> removeSubscriptionFromUser(
            @PathVariable UUID userId,
            @PathVariable UUID sub_id) {
        log.info("Удаление подписки с ID: {} у пользователя с ID: {}", sub_id, userId);
        subscriptionService.removeSubscriptionFromUser(userId, sub_id);
        return ResponseEntity.noContent().build();
    }
}
