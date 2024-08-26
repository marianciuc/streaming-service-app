package io.github.marianciuc.streamingservice.order.repository;

import io.github.marianciuc.streamingservice.order.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface OrderRepository extends MongoRepository<Order, UUID> {
}
