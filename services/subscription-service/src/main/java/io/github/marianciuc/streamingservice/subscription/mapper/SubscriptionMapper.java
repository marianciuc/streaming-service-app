package com.mv.streamingservice.subscription.mapper;

import com.mv.streamingservice.subscription.dto.SubscriptionRequest;
import com.mv.streamingservice.subscription.entity.Subscription;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionMapper {
    public Subscription toEntity(SubscriptionRequest dto){
        return Subscription.builder()
                .name(dto.name())
                .price(dto.price())
                .allowedResolutions(dto.allowedResolutions())
                .durationInDays(dto.durationInDays())
                .description(dto.description())
                .build();
    }
}
