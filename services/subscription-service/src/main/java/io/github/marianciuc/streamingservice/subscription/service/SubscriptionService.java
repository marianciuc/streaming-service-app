package io.github.marianciuc.streamingservice.subscription.service;

import io.github.marianciuc.streamingservice.subscription.dto.SubscriptionRequest;
import io.github.marianciuc.streamingservice.subscription.dto.SubscriptionResponse;
import io.github.marianciuc.streamingservice.subscription.entity.RecordStatus;
import io.github.marianciuc.streamingservice.subscription.entity.Subscription;
import io.github.marianciuc.streamingservice.subscription.exceptions.NotFoundException;
import io.github.marianciuc.streamingservice.subscription.mapper.SubscriptionMapper;
import io.github.marianciuc.streamingservice.subscription.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for managing subscriptions.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SubscriptionService {

    private final static String SUBSCRIPTION_NOT_FOUND_MSG = "Subscription not found";

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
        if (!subscription.getIsTemporary().equals(request.isTemporary())) subscription.setIsTemporary(request.isTemporary());
        // TODO send message to users about updates
    }


    public Subscription getSubscriptionById(UUID id){
        return subscriptionRepository.findById(id).orElseThrow(() -> new NotFoundException(SUBSCRIPTION_NOT_FOUND_MSG));
    }

    public SubscriptionResponse getSubscriptionResponse(UUID id) {
        return subscriptionMapper.toResponse(getSubscriptionById(id));
    }

    public List<SubscriptionResponse> getAllSubscriptions(){
        return subscriptionRepository.findAll().stream().map(subscriptionMapper::toResponse).collect(Collectors.toList());
    }
}
