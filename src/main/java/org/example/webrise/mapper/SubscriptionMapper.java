package org.example.webrise.mapper;

import org.example.webrise.dto.CreateSubscriptionRequest;
import org.example.webrise.dto.SubscriptionDto;
import org.example.webrise.entity.Subscription;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    Subscription toEntity(CreateSubscriptionRequest dto);

    SubscriptionDto toDto(Subscription subscription);
}
