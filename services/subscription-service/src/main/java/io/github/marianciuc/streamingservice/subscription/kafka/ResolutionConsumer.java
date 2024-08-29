package io.github.marianciuc.streamingservice.subscription.kafka;

import io.github.marianciuc.streamingservice.subscription.dto.ResolutionDto;
import io.github.marianciuc.streamingservice.subscription.service.ResolutionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static io.github.marianciuc.streamingservice.subscription.kafka.KafkaTopics.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResolutionConsumer {
    private final ResolutionService resolutionService;

    @KafkaListener(topics = RESOLUTION_CREATED_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    private void consumeResolutionCreate(ResolutionDto resolutionDto) {
        log.info("Kafka consumer consumeResolutionCreate: {}", resolutionDto);
        resolutionService.createResolution(resolutionDto);
    }

    @KafkaListener(topics = RESOLUTION_UPDATED_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    private void consumeResolutionUpdate(ResolutionDto resolutionDto) {
        log.info("Kafka consumer consumeResolutionUpdate: {}", resolutionDto);
        resolutionService.updateResolution(resolutionDto);
    }

    @KafkaListener(topics = RESOLUTION_DELETED_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    private void consumeResolutionDelete(UUID resolutionId) {
        log.info("Kafka consumer consumeResolutionDelete: {}", resolutionId);
        resolutionService.deleteResolution(resolutionId);
    }
}
