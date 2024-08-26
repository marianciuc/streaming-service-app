package io.github.marianciuc.streamingservice.order.client;

import io.github.marianciuc.streamingservice.order.dto.Subscription;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

@Service
public class SubscriptionClient {
    private RestTemplate restTemplate;

    public Subscription fetchSubscription(UUID uuid) {
        return null;
    }

    public Optional<Subscription> fetchActiveUserSubscription(UUID userId) {
        return null;
    }
}
