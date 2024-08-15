package com.mv.streamingservice.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The {@code User} class represents a user in the system.
 * @see Role
 * @see UserType
 * @see RecordStatus
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "users", indexes = {
        @Index(name = "idxEmail", columnList = "email"),
        @Index(name = "idxUsername", columnList = "username")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, name = "email")
    private String email;

    @Column(nullable = false, unique = true, name = "username", updatable = false)
    private String username;

    @Column(nullable = false, name = "passwordHash")
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "role")
    private Role role;

    @Column(nullable = false, name = "user_type", updatable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_banned")
    private Boolean isBanned;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "record_status", length = 30, columnDefinition = "varchar(30) default 'ACTIVE'")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private RecordStatus recordStatus = RecordStatus.ACTIVE;

    public boolean isRecordStatusDeleted() {
        return this.recordStatus == RecordStatus.DELETED;
    }

    public boolean isRecordStatusActive() {
        return this.recordStatus == RecordStatus.ACTIVE;
    }
}
