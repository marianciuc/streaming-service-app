package com.mv.streamingservice.content.dto.response;

import java.util.List;

public record PaginationResponse<T>(
        List<T> data,
        int currentPage,
        Long totalItems,
        int totalPages
) { }
