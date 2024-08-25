package com.mv.streamingservice.subscription.repository;

import com.mv.streamingservice.subscription.entity.Subscription;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface SubscriptionRepository extends MongoRepository<Subscription, UUID> {
}
