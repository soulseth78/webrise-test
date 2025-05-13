package org.example.webrise.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.webrise.dto.CreateSubscriptionRequest;
import org.example.webrise.dto.SubscriptionDto;
import org.example.webrise.entity.Subscription;
import org.example.webrise.entity.User;
import org.example.webrise.exception.NotFoundException;
import org.example.webrise.mapper.SubscriptionMapper;
import org.example.webrise.repository.SubscriptionRepository;
import org.example.webrise.repository.UserRepository;
import org.example.webrise.service.SubscriptionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;

    @Override
    public SubscriptionDto addSubscriptionToUser(UUID userId, CreateSubscriptionRequest request) {
        log.info("Добавление подписки '{}' пользователю с ID: {}", request.getServiceName(), userId);

        User user = findById(userId);

        Subscription subscription = subscriptionRepository
                .findByServiceName(request.getServiceName())
                .orElseGet(() -> {
                    Subscription newSub = new Subscription();
                    newSub.setServiceName(request.getServiceName());
                    return subscriptionRepository.save(newSub);
                });

        user.getSubscriptions().add(subscription);
        userRepository.save(user);

        return subscriptionMapper.toDto(subscription);
    }

    @Override
    public List<SubscriptionDto> getUserSubscriptions(UUID userId) {
        User user = findById(userId);

        log.info("Получение подписок пользователя с ID: {}", userId);
        return user.getSubscriptions().stream()
                .map(subscriptionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void removeSubscriptionFromUser(UUID userId, UUID subscriptionId) {
        User user = findById(userId);

        log.info("Удаление подписки ID: {} у пользователя ID: {}", subscriptionId, userId);
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new NotFoundException("Subscription not found with id: " + subscriptionId));

        user.getSubscriptions().remove(subscription);
        userRepository.save(user);
    }

    @Override
    public List<SubscriptionDto> getTopSubscriptions() {
        log.info("Получение топ- популярных подписок");
        List<Subscription> topSubscriptions = subscriptionRepository.findTopSubscriptions();

        // Ограничиваем результат до 3 самых популярных подписок
        return topSubscriptions.stream()
                .limit(3)  // Ограничиваем до 3
                .map(subscription -> new SubscriptionDto(
                        subscription.getId(),
                        subscription.getServiceName(),
                        subscription.getCreatedAt()))  // Используем ваш DTO без изменений
                .collect(Collectors.toList());
    }

    private User findById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("Пользователь с ID {} не найден", userId);
                    return new NotFoundException("User not found with id: " + userId);
                });
    }

}
