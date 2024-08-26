package io.github.marianciuc.streamingservice.payment.repository;

import io.github.marianciuc.streamingservice.payment.entity.PaymentMethod;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface PaymentMethodRepository extends MongoRepository<PaymentMethod, UUID> {

}
