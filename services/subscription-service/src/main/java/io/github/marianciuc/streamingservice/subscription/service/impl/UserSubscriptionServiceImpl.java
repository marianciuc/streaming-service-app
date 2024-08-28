/*
 * Copyright (c) 2024 Vladimir Marianciuc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *    all copies or substantial portions of the Software.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *     AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *     LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *     OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *      THE SOFTWARE.
 */

package io.github.marianciuc.streamingservice.subscription.service.impl;

import io.github.marianciuc.streamingservice.subscription.dto.OrderCreationEventKafkaDto;
import io.github.marianciuc.streamingservice.subscription.entity.Subscription;
import io.github.marianciuc.streamingservice.subscription.entity.SubscriptionStatus;
import io.github.marianciuc.streamingservice.subscription.entity.UserSubscriptions;
import io.github.marianciuc.streamingservice.subscription.repository.UserSubscriptionRepository;
import io.github.marianciuc.streamingservice.subscription.service.SubscriptionService;
import io.github.marianciuc.streamingservice.subscription.service.UserSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * UserSubscriptionService is a class that provides methods for managing user subscriptions.
 */
@Service
@RequiredArgsConstructor
public class UserSubscriptionServiceImpl implements UserSubscriptionService {

    private final UserSubscriptionRepository repository;
    private final SubscriptionService subscriptionService;


    public void subscribeUser(OrderCreationEventKafkaDto orderCreationEventKafkaDto) {
        Subscription subscription = subscriptionService.getSubscription(orderCreationEventKafkaDto.subscriptionId());
        UserSubscriptions userSubscription = UserSubscriptions.builder()
                .subscription(subscription)
                .orderId(orderCreationEventKafkaDto.orderId())
                .userId(orderCreationEventKafkaDto.userId())
                .status(SubscriptionStatus.ACTIVE)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(subscription.getDurationInDays()))
                .build();

        repository.save(userSubscription);
    }


    public List<UserSubscriptions> getAllUserSubscriptionsByStatusAndEndDate(SubscriptionStatus status, LocalDate endDate) {
        return repository.findAllByStatusAndEndDate(status, endDate);
    }


    public void cancelSubscription(UserSubscriptions subscription) {
        subscription.setStatus(SubscriptionStatus.CANCELLED);
        repository.save(subscription);
        // TODO отправить сообщение пользователю на почту что подписка отменена
    }

    public void extendSubscription(UserSubscriptions userSubscription) {
        Subscription subscription = userSubscription.getSubscription();
        if (subscription.getIsTemporary()) {
            // create new order request
        } else {

        }
    }

    public void unsubscribeUser(UserSubscriptions subscription) {
        subscription.setStatus(SubscriptionStatus.INACTIVE);
        repository.save(subscription);
        // Send topic to users to change role
        // send topic to notify user
    }
}