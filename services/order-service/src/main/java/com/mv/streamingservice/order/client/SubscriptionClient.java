package com.mv.streamingservice.order.client;

import com.mv.streamingservice.order.dto.Subscription;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class SubscriptionClient {
    private RestTemplate restTemplate;

    public Subscription fetchSubscription(UUID uuid) {
        return null;
    }
}
