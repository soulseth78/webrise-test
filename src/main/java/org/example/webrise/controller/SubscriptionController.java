package org.example.webrise.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.webrise.dto.SubscriptionDto;
import org.example.webrise.service.SubscriptionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/top")
    public List<SubscriptionDto> getTopSubscriptions() {
        log.info("Получение топ-3 популярных подписок");
        return subscriptionService.getTopSubscriptions();
    }

}


