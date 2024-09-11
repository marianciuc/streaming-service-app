package io.github.marianciuc.streamingservice.payment.controller;

import io.github.marianciuc.streamingservice.payment.dto.common.AddressDto;
import io.github.marianciuc.streamingservice.payment.dto.common.CardHolderDto;
import io.github.marianciuc.streamingservice.payment.dto.requests.CreateCartHolderRequest;
import io.github.marianciuc.streamingservice.payment.enums.Currency;
import io.github.marianciuc.streamingservice.payment.kafka.messages.InitializePaymentMessage;
import io.github.marianciuc.streamingservice.payment.kafka.messages.PaymentStatusMessage;
import io.github.marianciuc.streamingservice.payment.service.AddressService;
import io.github.marianciuc.streamingservice.payment.service.CardHolderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final KafkaTemplate<String, InitializePaymentMessage> kafkaTemplate;

    private final CardHolderService cardHolderService;
    private final AddressService addressService;

    @PostMapping("/card-holder")
    public ResponseEntity<CardHolderDto> createCardHolder(@Valid @RequestBody CreateCartHolderRequest request) {
        return ResponseEntity.ok(cardHolderService.createCardHolder(request));
    }

//    @PutMapping("/payment-method")
//    public ResponseEntity<Void> updatePaymentMethod(@RequestBody PaymentMethodRequest request) {
//        paymentMethodService.updatePaymentMethod(request);
//        return ResponseEntity.ok().build();
//    }

    @PutMapping("/card-holder")
    public ResponseEntity<Void> updateCardHolder(@RequestBody CreateCartHolderRequest request) {
//        cardHolderService.updateCardHolder(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/address")
    public ResponseEntity<Void> updateAddress(@RequestBody AddressDto request) {
        addressService.updateAddress(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/test")
    public ResponseEntity<Void> test() {
        Message<InitializePaymentMessage> message = MessageBuilder
                .withPayload(new InitializePaymentMessage(UUID.randomUUID(), UUID.fromString("7b23b971-1111-49f1-ab34" +
                        "-203b65c4c66e"), Currency.USD, 1L))
                .setHeader(KafkaHeaders.TOPIC, "start-payment-processing")
                .build();
        kafkaTemplate.send(message);
        return ResponseEntity.ok().build();
    }
}
