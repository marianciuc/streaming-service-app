package io.github.marianciuc.streamingservice.subscription.service.impl;

import io.github.marianciuc.streamingservice.subscription.entity.RecordStatus;
import io.github.marianciuc.streamingservice.subscription.entity.Subscription;
import io.github.marianciuc.streamingservice.subscription.exceptions.NotFoundException;
import io.github.marianciuc.streamingservice.subscription.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
public class SubscriptionServiceImplTest {

    @Mock
    private SubscriptionRepository subscriptionRepositoryMock;

    @InjectMocks
    private SubscriptionServiceImpl subscriptionServiceImplUnderTest;

    private final Subscription subscription = new Subscription();

    @Test
    public void shouldDeleteSubscription() {
        UUID id = UUID.randomUUID();
        subscription.setRecordStatus(RecordStatus.ACTIVE);
        when(subscriptionRepositoryMock.findById(any(UUID.class))).thenReturn(Optional.of(subscription));
        
        subscriptionServiceImplUnderTest.deleteSubscription(id);
        
        Assertions.assertEquals(RecordStatus.DELETED, subscription.getRecordStatus());
        Mockito.verify(subscriptionRepositoryMock, Mockito.times(1)).save(any(Subscription.class));
    }

    @Test
    public void shouldThrowExceptionWhenSubscriptionNotFound() {
        UUID id = UUID.randomUUID();
        when(subscriptionRepositoryMock.findById(any(UUID.class))).thenReturn(Optional.empty());
        
        Assertions.assertThrows(NotFoundException.class, () -> subscriptionServiceImplUnderTest.deleteSubscription(id));
    }
}