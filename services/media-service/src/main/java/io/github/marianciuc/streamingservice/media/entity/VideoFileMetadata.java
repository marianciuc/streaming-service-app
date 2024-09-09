package io.github.marianciuc.streamingservice.media.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "medias")
public class VideoFileMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "play_list_path")
    private String playListPath;

    @ManyToOne(fetch = FetchType.LAZY)
    private Resolution resolution;

    @ManyToOne(fetch = FetchType.LAZY)
    private Video video;

    @Column(name = "is_processed")
    private Boolean isProcessed;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP()", updatable = false, insertable = false)
    private LocalDateTime createdAt;
}
