/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CardHolderServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.payment.service.impl;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import io.github.marianciuc.streamingservice.payment.dto.common.AddressDto;
import io.github.marianciuc.streamingservice.payment.dto.common.CardHolderDto;
import io.github.marianciuc.streamingservice.payment.dto.requests.CreateCartHolderRequest;
import io.github.marianciuc.streamingservice.payment.entity.Address;
import io.github.marianciuc.streamingservice.payment.entity.CardHolder;
import io.github.marianciuc.streamingservice.payment.enums.CardStatus;
import io.github.marianciuc.streamingservice.payment.repository.CardHolderRepository;
import io.github.marianciuc.streamingservice.payment.service.AddressService;
import io.github.marianciuc.streamingservice.payment.service.CardHolderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardHolderServiceImpl implements CardHolderService {

    private final AddressService addressService;
    private final CardHolderRepository repository;

    @Override
    @Transactional
    public CardHolderDto createCardHolder(CreateCartHolderRequest request) {
        if (repository.existsCardHolderByUserId(request.id())) {
            throw new IllegalArgumentException("Card holder already exists");
        }

        Address address = addressService.addAddress(request.address());

        CustomerCreateParams params = CustomerCreateParams.builder()
                .setName(request.cardHolderName())
                .setEmail(request.email())
                .setSource(request.token())
                .setPhone(request.phoneNumber())
                .setAddress(CustomerCreateParams.Address.builder().setCity(address.getCity()).setCountry(address.getCountry())
                        .setLine1(address.getLine1()).setPostalCode(address.getPostalCode())
                        .setState(address.getState()).build())
                .build();

        try {
            Customer customer = Customer.create(params);
            CardHolder cardHolder = CardHolder.builder()
                    .address(address)
                    .cardHolderName(request.cardHolderName())
                    .email(request.email())
                    .stripeCustomerId(customer.getId())
                    .phoneNumber(request.phoneNumber())
                    .cardStatus(CardStatus.ACTIVE)
                    .userId(request.id())
                    .build();
            return CardHolderDto.toDto(repository.save(cardHolder));
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CardHolderDto getCardHolder(UUID cardHolderId) {
        return repository.findById(cardHolderId).map(CardHolderDto::toDto).orElseThrow(() -> new IllegalArgumentException("Card " +
                "holder not found"));
    }
}
