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
import java.util.Optional;
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

        // Проверка, есть ли у этого пользователя подписка с таким serviceName
        Optional<Subscription> existing = user.getSubscriptions().stream()
                .filter(s -> s.getServiceName().equalsIgnoreCase(request.getServiceName()))
                .findFirst();

        if (existing.isPresent()) {
            throw new IllegalStateException("Подписка уже добавлена пользователю");
        }

        Subscription subscription = new Subscription();
        subscription.setServiceName(request.getServiceName());
        subscription.setUser(user);

        Subscription saved = subscriptionRepository.save(subscription);

        return subscriptionMapper.toDto(saved);
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
        log.info("Получение топ-популярных подписок");

        List<Object[]> topServiceData = subscriptionRepository.findTopServiceNames();

        return topServiceData.stream()
                .limit(3)
                .map(obj -> new SubscriptionDto(
                        null, // ID отсутствует
                        (String) obj[0], // serviceName
                        null  // createdAt — не нужен для статистики
                ))
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
