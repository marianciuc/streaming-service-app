package io.github.marianciuc.streamingservice.subscription.repository;

import io.github.marianciuc.streamingservice.subscription.entity.SubscriptionStatus;
import io.github.marianciuc.streamingservice.subscription.entity.UserSubscriptions;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface UserSubscriptionRepository extends MongoRepository<UserSubscriptions, UUID> {
    List<UserSubscriptions> findAllByStatus(SubscriptionStatus status);
    List<UserSubscriptions> findAllByStatusAndEndDateBetween(SubscriptionStatus status, LocalDate startDate, LocalDate endDate);
}
