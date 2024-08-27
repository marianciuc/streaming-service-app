package io.github.marianciuc.streamingservice.subscription.repository;

import io.github.marianciuc.streamingservice.subscription.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
}
