/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: AddressServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.payment.service.impl;

import io.github.marianciuc.streamingservice.payment.dto.common.AddressDto;
import io.github.marianciuc.streamingservice.payment.entity.Address;
import io.github.marianciuc.streamingservice.payment.entity.JWTUserPrincipal;
import io.github.marianciuc.streamingservice.payment.repository.AddressRepository;
import io.github.marianciuc.streamingservice.payment.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository repository;

    @Override
    public Address addAddress(AddressDto addressDto) {
        Address address = Address.builder()
                .city(addressDto.city())
                .state(addressDto.state())
                .line1(addressDto.line1())
                .line2(addressDto.line2())
                .country(addressDto.country())
                .postalCode(addressDto.postalCode())
                .build();
        return repository.save(address);
    }

    @Override
    public void updateAddress(AddressDto req) {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof JWTUserPrincipal jwtUserPrincipal) {
            Address address = repository.findById(req.id()).orElseThrow(() -> new RuntimeException("Address not found"));
            if (!req.city().isEmpty()) address.setCity(req.city());
            if (!req.state().isEmpty()) address.setState(req.state());
            if (!req.line1().isEmpty()) address.setLine1(req.line1());
            if (!req.line2().isEmpty()) address.setLine2(req.line2());
            if (!req.country().isEmpty()) address.setCountry(req.country());
            if (!req.postalCode().isEmpty()) address.setPostalCode(req.postalCode());
            repository.save(address);
        } else {
            throw new RuntimeException("User not authenticated");
        }
    }

    @Override
    public void deleteAddress(UUID id) {

    }
}
