/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: AddressRepository.java
 *
 */

package io.github.marianciuc.streamingservice.payment.repository;

import io.github.marianciuc.streamingservice.payment.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {

}
