package io.github.marianciuc.streamingservice.subscription.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "resolutions")
@Builder
@RequiredArgsConstructor
public class Resolution {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, name = "name", unique = true)
    private String name;

    @Column(nullable = false, name = "description")
    private String description;

    @ManyToMany(mappedBy = "resolutions")
    private Set<Subscription> subscriptionSet;
}
