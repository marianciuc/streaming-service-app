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
import com.stripe.param.CustomerUpdateParams;
import io.github.marianciuc.streamingservice.payment.dto.common.CardHolderDto;
import io.github.marianciuc.streamingservice.payment.dto.requests.CreateCartHolderRequest;
import io.github.marianciuc.streamingservice.payment.dto.requests.UpdateCardHolderRequest;
import io.github.marianciuc.streamingservice.payment.entity.Address;
import io.github.marianciuc.streamingservice.payment.entity.CardHolder;
import io.github.marianciuc.streamingservice.payment.entity.JWTUserPrincipal;
import io.github.marianciuc.streamingservice.payment.enums.CardStatus;
import io.github.marianciuc.streamingservice.payment.repository.CardHolderRepository;
import io.github.marianciuc.streamingservice.payment.service.AddressService;
import io.github.marianciuc.streamingservice.payment.service.CardHolderService;
import io.github.marianciuc.streamingservice.payment.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardHolderServiceImpl implements CardHolderService {

    private final AddressService addressService;
    private final CardHolderRepository repository;
    private final UserService userService;

    @Override
    @Transactional
    public CardHolderDto createCardHolder(CreateCartHolderRequest request) {
        UUID userId = this.userService.extractUserIdFromAuth();

        if (repository.existsCardHolderByUserId(userId))
            throw new IllegalArgumentException("Card holder already exists");

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
                    .userId(userId)
                    .build();
            return CardHolderDto.toDto(this.repository.save(cardHolder));
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CardHolderDto findCardHolder(UUID cardHolderId) {
        if (this.userService.hasAdminRoles()) {
            UUID idToFetch = (cardHolderId != null) ? cardHolderId : userService.extractUserIdFromAuth();
            return CardHolderDto.toDto(this.findCardHolderEntity(idToFetch));
        }
        return CardHolderDto.toDto(this.findCardHolderEntity(this.userService.extractUserIdFromAuth()));
    }

    @Override
    public void updatePaymentMethod(String token) {
        CardHolder cardHolder = this.findCardHolderEntity(this.userService.extractUserIdFromAuth());
        try {
            Customer customer = Customer.retrieve(cardHolder.getStripeCustomerId());
            customer.update(CustomerUpdateParams.builder().setSource(token).build());
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }

    public CardHolder findCardHolderEntity(UUID cardHolderId) {
        return this.repository.findById(cardHolderId).orElseThrow(() -> new IllegalArgumentException("Card holder not found"));
    }

    @Override
    public void updateCardHolder(UpdateCardHolderRequest request) {
        CardHolder cardHolder = this.findCardHolderEntity(((JWTUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId());
        if (!request.cardHolderName().isEmpty()) cardHolder.setCardHolderName(request.cardHolderName());
        if (!request.cardHolderName().isEmpty()) cardHolder.setPhoneNumber(request.phoneNumber());
        if (!request.cardHolderName().isEmpty()) cardHolder.setEmail(request.email());
        this.repository.save(cardHolder);
    }
}
