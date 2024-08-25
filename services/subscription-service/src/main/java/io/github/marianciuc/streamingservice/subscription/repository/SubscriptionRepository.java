package io.github.marianciuc.streamingservice.subscription.repository;

import io.github.marianciuc.streamingservice.subscription.entity.Subscription;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface SubscriptionRepository extends MongoRepository<Subscription, UUID> {
}
