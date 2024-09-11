/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: AddressDto.java
 *
 */

package io.github.marianciuc.streamingservice.payment.dto.common;

import io.github.marianciuc.streamingservice.payment.entity.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public record AddressDto(
        UUID id,
        @NotBlank(message = "Line1 is mandatory")
        String line1,
        String line2,

        @NotBlank(message = "City is mandatory")
        @Size(min = 2, max = 50, message = "City must be between 2 and 50 characters")
        String city,

        @NotBlank(message = "Postal code is mandatory")
        String postalCode,

        @NotBlank(message = "State is mandatory")
        @Size(min = 2, max = 50, message = "State must be between 2 and 50 characters")
        String state,

        @NotBlank(message = "Country is mandatory")
        @Size(min = 2, max = 50, message = "Country must be between 2 and 50 characters")
        String country
) {
        public static Address toEntity(AddressDto addressDto) {
                return Address.builder().id(addressDto.id).build();
        }
        public static AddressDto toDto(Address address) {
                return new AddressDto(address.getId(), address.getLine1(), address.getLine2(), address.getCity(),
                        address.getPostalCode(), address.getState(), address.getCountry());
        }
}
