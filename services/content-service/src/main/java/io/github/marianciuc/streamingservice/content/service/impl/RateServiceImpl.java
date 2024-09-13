/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: RateServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.content.service.impl;

import io.github.marianciuc.streamingservice.content.entity.Content;
import io.github.marianciuc.streamingservice.content.entity.Rate;
import io.github.marianciuc.streamingservice.content.repository.RateRepository;
import io.github.marianciuc.streamingservice.content.service.ContentService;
import io.github.marianciuc.streamingservice.content.service.RateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RateServiceImpl implements RateService {

    private final RateRepository repository;
    private final ContentService contentService;

    @Override
    public void rateContent(UUID id, Integer rate) {
        log.info("Content with id {} was rated with {}", id, rate);
        Content content = contentService.getContentEntity(id);
        Rate rateEntity = Rate.builder()
                .content(content)
                .userId(UUID.randomUUID())
                .rate(rate)
                .build();
        repository.save(rateEntity);
        content.setRating(calculateRating());
    }

    private BigDecimal calculateRating(List<Rate> rates) {
        BigDecimal sum = rates.stream().map(rate -> BigDecimal.valueOf(rate.getRate())).reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum.divide(BigDecimal.valueOf(rates.size()));
    }
}
