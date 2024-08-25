package com.mv.streamingservice.subscription.service;

import com.mv.streamingservice.subscription.dto.SubscriptionRequest;
import com.mv.streamingservice.subscription.dto.SubscriptionResponse;
import com.mv.streamingservice.subscription.entity.RecordStatus;
import com.mv.streamingservice.subscription.entity.Subscription;
import com.mv.streamingservice.subscription.exceptions.NotFoundException;
import com.mv.streamingservice.subscription.mapper.SubscriptionMapper;
import com.mv.streamingservice.subscription.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;


    public void delete(UUID id) {
        Subscription subscription = getSubscriptionById(id);
        subscription.setRecordStatus(RecordStatus.DELETED);
        subscriptionRepository.save(subscription);
    }

    public UUID createSubscription(SubscriptionRequest request) {
        Subscription subscription = subscriptionMapper.toEntity(request);
        return subscriptionRepository.save(subscription).getId();
    }

    public void updateSubscription(UUID id, SubscriptionRequest request) {
        Subscription subscription = getSubscriptionById(id);
        if (!subscription.getAllowedActiveSessions().equals(request.allowedActiveSessions())) subscription.setAllowedActiveSessions(request.allowedActiveSessions());
        // TODO send message to users about updates
    }

    public Subscription getSubscriptionById(UUID id){
        return subscriptionRepository.findById(id).orElseThrow(() -> new NotFoundException("Subscription not found"));
    }

    public List<SubscriptionResponse> getAllSubscriptions(){
        return null;
    }
}
