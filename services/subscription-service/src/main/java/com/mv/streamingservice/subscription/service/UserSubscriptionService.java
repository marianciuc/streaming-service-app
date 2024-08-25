package com.mv.streamingservice.subscription.service;

import com.mv.streamingservice.subscription.entity.SubscriptionStatus;
import com.mv.streamingservice.subscription.entity.UserSubscriptions;
import com.mv.streamingservice.subscription.repository.UserSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSubscriptionService {

    private static final int BATCH_SIZE = 1000;

    private final UserSubscriptionRepository repository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void fetchCanceledSubscriptions() {
        LocalDate now = LocalDate.now();
        List<UserSubscriptions> cancelledSubscriptions = repository.findAllByStatusAndEndDateBetween(SubscriptionStatus.CANCELLED, now.minusDays(1), now);

        for (UserSubscriptions subscription : cancelledSubscriptions) {
            if (subscription.getEndDate().isBefore(now))
                deactivateSubscription(subscription);
        }

    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void fetchToTryExpandSubscriptions() {
        LocalDate now = LocalDate.now();
        LocalDate start = now;
        LocalDate end = start.plusDays(2);

        List<UserSubscriptions> activeSubscriptions = repository.findAllByStatusAndEndDateBetween(SubscriptionStatus.ACTIVE, start, end);

        for (UserSubscriptions subscription : activeSubscriptions) {
            if (subscription.getEndDate().isBefore(now))
                deactivateSubscription(subscription);
        }

    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void fetchActiveSubscriptions() {
        LocalDate now = LocalDate.now();
        LocalDate start = now;
        LocalDate end = start.plusDays(2);

        List<UserSubscriptions> activeSubscriptions = repository.findAllByStatusAndEndDateBetween(SubscriptionStatus.ACTIVE, start, end);

        for (UserSubscriptions subscription : activeSubscriptions) {
            if (subscription.getEndDate().isBefore(now)) {
                deactivateSubscription(subscription);
            }
        }
    }

    private void extendSubscription(UserSubscriptions subscription) {
        LocalDate extendedDate = subscription.getEndDate().plusDays(7);
        subscription.setEndDate(extendedDate);
        repository.save(subscription);
    }

    private void deactivateSubscription(UserSubscriptions subscription) {
        subscription.setStatus(SubscriptionStatus.INACTIVE);
        repository.save(subscription);
        // Send topic to users to change role
        // send topic to notify user
    }
}