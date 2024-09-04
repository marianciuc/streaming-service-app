package io.github.marianciuc.streamingservice.media.entity;

import io.github.marianciuc.streamingservice.media.enums.MediaType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
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

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "content_length")
    long contentLength;

    @Column(name = "author_id")
    private UUID authorId;

    @Column(name = "content_id")
    private UUID contentId;

    @Column(name = "file_name")
    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Resolution resolution;

    @Enumerated(EnumType.STRING)
    @Column(name = "media_type")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private MediaType mediaType;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP()", updatable = false, insertable = false)
    private LocalDateTime createdAt;
}
