package io.github.marianciuc.streamingservice.media.kafka;

/**
 * This class provides constants for Kafka topics that are used in the application.
 */
public class KafkaTopics {
    public final static String RESOLUTION_CREATED_TOPIC = "resolution-created-topic";
    public final static String RESOLUTION_UPDATED_TOPIC = "resolution-updated-topic";
    public final static String RESOLUTION_DELETED_TOPIC = "resolution-deleted-topic";
    public final static String IMAGE_DELETED_TOPIC = "image-deleted-topic";
    public final static String VIDEO_DELETED_TOPIC = "video-deleted-topic";
    public final static String VIDEO_UPLOADED_TOPIC = "video-uploaded-topic";
}
