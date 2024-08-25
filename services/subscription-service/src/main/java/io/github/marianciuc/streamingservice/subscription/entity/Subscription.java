package com.mv.streamingservice.subscription.entity;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@Document
public class Subscription {
    @Id
    private UUID id;
    private String name;
    private String description;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private Integer durationInDays;
    private BigDecimal price;
    private List<Resolution> allowedResolutions;
    private Integer allowedActiveSessions;
    private RecordStatus recordStatus;
    private Boolean isTemporary;
    private UUID nextSubscriptionId;
}
