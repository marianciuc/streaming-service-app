/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: PaginationResponse.java
 *
 */

package io.github.marianciuc.streamingservice.payment.dto.responses;

public record PaginationResponse<T>(
        Integer pages,
        Integer currentPage,
        Integer pageSize,
        T data
) {
}
