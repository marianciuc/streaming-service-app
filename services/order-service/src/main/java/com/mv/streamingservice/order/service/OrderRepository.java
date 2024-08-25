package com.mv.streamingservice.order.service;

import com.mv.streamingservice.order.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface OrderRepository extends MongoRepository<Order, UUID> {
}
