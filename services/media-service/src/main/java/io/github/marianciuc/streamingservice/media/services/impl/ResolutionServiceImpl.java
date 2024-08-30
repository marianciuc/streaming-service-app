/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ResolutionServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.media.services.impl;

import io.github.marianciuc.streamingservice.media.dto.ResolutionRequest;
import io.github.marianciuc.streamingservice.media.dto.ResolutionResponse;
import io.github.marianciuc.streamingservice.media.entity.Resolution;
import io.github.marianciuc.streamingservice.media.exceptions.NotFoundException;
import io.github.marianciuc.streamingservice.media.kafka.KafkaResolutionProducer;
import io.github.marianciuc.streamingservice.media.mappers.ResolutionMapper;
import io.github.marianciuc.streamingservice.media.repository.ResolutionRepository;
import io.github.marianciuc.streamingservice.media.services.ResolutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResolutionServiceImpl implements ResolutionService {

    private final ResolutionRepository resolutionRepository;
    private final KafkaResolutionProducer kafkaResolutionProducer;
    private final ResolutionMapper resolutionMapper;

    @Override
    public ResolutionResponse createResolution(ResolutionRequest request) {
        return null;
    }

    @Override
    public ResolutionResponse updateResolution(ResolutionRequest request) {
        return null;
    }

    @Override
    public List<ResolutionResponse> getAllResolutions() {
        return List.of();
    }

    @Override
    public ResolutionResponse getResolutionById(UUID id) {
        return null;
    }

    @Override
    public Resolution getEntityById(UUID id) {
        return null;
    }

    @Override
    public void deleteResolution(UUID id) {
        Resolution r = resolutionRepository.findById(id).orElseThrow(() -> new NotFoundException("Resolution not founded"));
        ResolutionResponse resolutionResponse = resolutionMapper.toResponse(r);
        kafkaResolutionProducer.sendDeleteResolutionTopic(resolutionResponse);
        resolutionRepository.delete(r);
    }

    @Override
    public List<Resolution> getAllEntities() {
        return resolutionRepository.findAll();
    }
}
