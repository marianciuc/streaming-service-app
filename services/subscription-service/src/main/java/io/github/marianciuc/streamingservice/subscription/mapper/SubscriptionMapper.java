package io.github.marianciuc.streamingservice.subscription.mapper;

import io.github.marianciuc.streamingservice.subscription.dto.SubscriptionRequest;
import io.github.marianciuc.streamingservice.subscription.dto.SubscriptionResponse;
import io.github.marianciuc.streamingservice.subscription.entity.Subscription;
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

    public SubscriptionResponse toResponse(Subscription subscription) {
        return new SubscriptionResponse(
                subscription.getId(),
                subscription.getName(),
                subscription.getDescription(),
                subscription.getAllowedActiveSessions(),
                subscription.getDurationInDays(),
                subscription.getPrice(),
                subscription.getIsTemporary(),
                subscription.getNextSubscriptionId(),
                subscription.getUpdatedAt(),
                subscription.getRecordStatus(),
                subscription.getCreatedAt()
        );
    }
}
