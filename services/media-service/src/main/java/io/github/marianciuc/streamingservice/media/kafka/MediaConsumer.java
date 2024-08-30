package io.github.marianciuc.streamingservice.media.kafka;

import io.github.marianciuc.streamingservice.media.dto.MediaDeleteMessage;
import io.github.marianciuc.streamingservice.media.services.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static io.github.marianciuc.streamingservice.media.kafka.KafkaTopics.VIDEO_DELETED_TOPIC;


@Service
@Slf4j
@RequiredArgsConstructor
public class MediaConsumer {

    private final VideoService videoService;

    @KafkaListener(topics = VIDEO_DELETED_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    private void consumeVideDeletedCreate(MediaDeleteMessage mediaDto) {
        log.info("Kafka consumer consumeResolutionCreate: {}", mediaDto);
        videoService.deleteVideoByContent(mediaDto.contentId());
    }
}
