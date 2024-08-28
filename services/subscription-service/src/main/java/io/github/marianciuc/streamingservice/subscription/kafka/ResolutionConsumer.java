/*
 * Copyright (c) 2024 Vladimir Marianciuc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *    all copies or substantial portions of the Software.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *     AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *     LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *     OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *      THE SOFTWARE.
 */

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
