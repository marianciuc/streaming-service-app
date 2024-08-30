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

package io.github.marianciuc.streamingservice.subscription.service;

import io.github.marianciuc.jwtsecurity.service.JwtUserDetails;
import io.github.marianciuc.streamingservice.subscription.dto.OrderCreationEventKafkaDto;
import io.github.marianciuc.streamingservice.subscription.dto.UserSubscriptionDto;
import io.github.marianciuc.streamingservice.subscription.entity.SubscriptionStatus;
import io.github.marianciuc.streamingservice.subscription.entity.UserSubscriptions;

import javax.naming.OperationNotSupportedException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * UserSubscriptionService is an interface that provides methods for managing user subscriptions.
 */
public interface UserSubscriptionService {
    void subscribeUser(OrderCreationEventKafkaDto req);

    void unsubscribeUser(UserSubscriptions userSubscription) throws OperationNotSupportedException;

    void extendSubscription(UserSubscriptions userSubscription) throws IOException, OperationNotSupportedException;

    void cancelSubscription(UserSubscriptions userSubscription) throws OperationNotSupportedException;
    void cancelSubscription(UUID id);
    void cancelSubscription(JwtUserDetails jwtUserDetails);

    List<UserSubscriptions> getAllUserSubscriptionsByStatusAndEndDate(SubscriptionStatus status, LocalDate endDate);

    UserSubscriptionDto getActiveSubscription(JwtUserDetails jwtUserDetails, UUID uuid);
}
