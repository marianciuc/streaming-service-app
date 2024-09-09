package io.github.marianciuc.streamingservice.media.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.util.UUID;

@Data
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "files")
public class VideoFileMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "play_list_path")
    private String playListPath;

    @ManyToOne(fetch = FetchType.LAZY)
    private Resolution resolution;

    @ManyToOne
    @JoinColumn(name = "video_id")
    private Video video;

    @Column(name = "is_processed")
    private Boolean isProcessed;
}
