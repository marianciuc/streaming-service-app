package com.mv.streamingservice.content.entity;

import com.mv.streamingservice.content.enums.RecordStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@MappedSuperclass
public class BaseEntity {
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
}
