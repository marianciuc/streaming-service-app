/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CreateCartHolderRequest.java
 *
 */

package io.github.marianciuc.streamingservice.payment.dto.requests;

import io.github.marianciuc.streamingservice.payment.dto.common.AddressDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public record CreateCartHolderRequest(

        @NotNull(message = "User id is required")
        UUID id,

        @NotNull(message = "Address is required")
        AddressDto address,

        @NotBlank(message = "Token is mandatory")
        String token,

        @NotBlank(message = "Card holder name is mandatory")
        @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name must contain only letters and spaces")
        String cardHolderName,

        @NotBlank(message = "Phone number is mandatory")
        @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Phone number is invalid")
        String phoneNumber,

        @Email(message = "Email should be valid")
        String email
) { }
