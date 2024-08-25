package io.github.marianciuc.streamingservice.media.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@MappedSuperclass
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "media")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP()", updatable = false, insertable = false)
    private LocalDateTime createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP()", insertable = false)
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "record_status", length = 30, columnDefinition = "varchar(30) default 'ACTIVE'")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private RecordStatus recordStatus = RecordStatus.ACTIVE;

    @Column(name = "content_type")
    private String contentType;

    @Lob
    @JsonIgnore
    @Column(name = "data", columnDefinition = "LONGBLOB")
    private byte[] data;

    public boolean isRecordStatusDeleted() {
        return this.recordStatus == RecordStatus.DELETED;
    }
}
