/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: AddressService.java
 *
 */

package io.github.marianciuc.streamingservice.payment.service;

import io.github.marianciuc.streamingservice.payment.dto.common.AddressDto;
import io.github.marianciuc.streamingservice.payment.entity.Address;

import java.util.UUID;

public interface AddressService {
    Address addAddress(AddressDto address);
    void updateAddress(AddressDto address);
    void deleteAddress(UUID id);
}
