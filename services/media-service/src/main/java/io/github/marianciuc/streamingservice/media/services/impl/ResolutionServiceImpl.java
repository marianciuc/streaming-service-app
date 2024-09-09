/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: ResolutionServiceImpl.java
 *
 */

package io.github.marianciuc.streamingservice.media.services.impl;

import io.github.marianciuc.streamingservice.media.dto.ResolutionDto;
import io.github.marianciuc.streamingservice.media.entity.Resolution;
import io.github.marianciuc.streamingservice.media.exceptions.NotFoundException;
import io.github.marianciuc.streamingservice.media.kafka.KafkaResolutionProducer;
import io.github.marianciuc.streamingservice.media.kafka.messages.ResolutionMessage;
import io.github.marianciuc.streamingservice.media.repository.ResolutionRepository;
import io.github.marianciuc.streamingservice.media.services.ResolutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResolutionServiceImpl implements ResolutionService {

    private final static String RESOLUTION_NOT_FOUND_MSG = "Resolution not found";

    private final ResolutionRepository resolutionRepository;
    private final KafkaResolutionProducer kafkaResolutionProducer;

    @Override
    public ResolutionDto createResolution(ResolutionDto request) {
        Resolution resolution = Resolution.builder()
                .id(UUID.randomUUID())
                .name(request.name())
                .width(request.width())
                .height(request.height())
                .bitrate(request.bitrate())
                .description(request.description())
                .build();

        resolutionRepository.save(resolution);
        kafkaResolutionProducer.sendCreatedResolutionTopic(ResolutionMessage.toResolutionMessage(resolution));
        return ResolutionDto.toResolutionDto(resolution);
    }

    @Override
    public ResolutionDto updateResolution(ResolutionDto request) {
        Resolution existingResolution = resolutionRepository.findById(request.id())
                .orElseThrow(() -> new NotFoundException(RESOLUTION_NOT_FOUND_MSG + ": " + request.id()));

        existingResolution.setName(request.name());
        existingResolution.setWidth(request.width());
        existingResolution.setHeight(request.height());
        existingResolution.setBitrate(request.bitrate());
        existingResolution.setDescription(request.description());

        Resolution updatedResolution = resolutionRepository.save(existingResolution);

        kafkaResolutionProducer.sendUpdateResolutionTopic(ResolutionMessage.toResolutionMessage(updatedResolution));
        return ResolutionDto.toResolutionDto(updatedResolution);
    }

    @Override
    public List<ResolutionDto> getAllResolutions() {
        return resolutionRepository.findAll().stream().map(ResolutionDto::toResolutionDto).toList();
    }

    @Override
    public ResolutionDto getResolutionById(UUID id) {
        Resolution resolution = resolutionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(RESOLUTION_NOT_FOUND_MSG + ": " + id));
        return ResolutionDto.toResolutionDto(resolution);
    }

    @Override
    public Resolution getEntityById(UUID id) {
        return resolutionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(RESOLUTION_NOT_FOUND_MSG + ": " + id));
    }

    @Override
    public void deleteResolution(UUID id) {
        Resolution r = resolutionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(RESOLUTION_NOT_FOUND_MSG + ": " + id));

        kafkaResolutionProducer.sendDeleteResolutionTopic(ResolutionMessage.toResolutionMessage(r));
        resolutionRepository.delete(r);
    }

    @Override
    public List<Resolution> getAllEntities() {
        return resolutionRepository.findAll();
    }
}