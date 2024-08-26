package io.github.marianciuc.streamingservice.subscription.service;

import io.github.marianciuc.streamingservice.subscription.dto.OrderCreationEventKafkaDto;
import io.github.marianciuc.streamingservice.subscription.entity.Subscription;
import io.github.marianciuc.streamingservice.subscription.entity.SubscriptionStatus;
import io.github.marianciuc.streamingservice.subscription.entity.UserSubscriptions;
import io.github.marianciuc.streamingservice.subscription.repository.UserSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * UserSubscriptionService is a class that provides methods for managing user subscriptions.
 */
@Service
@RequiredArgsConstructor
public class UserSubscriptionService {

    private final UserSubscriptionRepository repository;
    private final SubscriptionService subscriptionService;
    private final OrderClient orderClient;

    /**
     * Subscribes a user to a specific subscription.
     *
     * @param orderCreationEventKafkaDto the OrderCreationEventKafkaDto object containing the subscription details
     */
    public void subscribeUser(OrderCreationEventKafkaDto orderCreationEventKafkaDto) {
        Subscription subscription = subscriptionService.getSubscriptionById(orderCreationEventKafkaDto.subscriptionId());
        UserSubscriptions userSubscription = UserSubscriptions.builder()
                .subscriptionId(subscription.getId())
                .orderId(orderCreationEventKafkaDto.orderId())
                .userId(orderCreationEventKafkaDto.userId())
                .status(SubscriptionStatus.ACTIVE)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(subscription.getDurationInDays()))
                .build();

        repository.save(userSubscription);
    }

    /**
     * Get all subscriptions by status and end date list.
     *
     * @param status  the status
     * @param endDate the end date
     * @return the list
     */
    public List<UserSubscriptions> getAllSubscriptionsByStatusAndEndDate(SubscriptionStatus status, LocalDate endDate) {
        return repository.findAllByStatusAndEndDate(status, endDate);
    }

    /**
     * Cancel subscription.
     *
     * @param subscription the subscription
     */
    public void cancelSubscription(UserSubscriptions subscription) {
        subscription.setStatus(SubscriptionStatus.CANCELLED);
        repository.save(subscription);
        // TODO отправить сообщение пользователю на почту что подписка отменена
    }

    private void extendSubscription(UserSubscriptions userSubscription) {
        Subscription subscription = subscriptionService.getSubscriptionById(userSubscription.getSubscriptionId());
        if (subscription.getIsTemporary()) {
            // create new order request
        } else {

        }
    }

    /**
     * Deactivate subscription.
     *
     * @param subscription the subscription
     */
    public void deactivateSubscription(UserSubscriptions subscription) {
        subscription.setStatus(SubscriptionStatus.INACTIVE);
        repository.save(subscription);
        // Send topic to users to change role
        // send topic to notify user
    }
}