package io.github.marianciuc.streamingservice.subscription.repository;

import io.github.marianciuc.streamingservice.subscription.entity.SubscriptionStatus;
import io.github.marianciuc.streamingservice.subscription.entity.UserSubscriptions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscriptions, UUID> {
    List<UserSubscriptions> findAllByStatusAndEndDate(SubscriptionStatus status, LocalDate endDate);
}
