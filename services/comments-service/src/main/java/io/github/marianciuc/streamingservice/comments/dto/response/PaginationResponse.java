/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: PaginationResponse.java
 *
 */

package io.github.marianciuc.streamingservice.comments.dto.response;

import java.util.List;

public record PaginationResponse<T>(
        Integer pages,
        Integer currentPage,
        Integer pageSize,
        List<T> data
) {
}
