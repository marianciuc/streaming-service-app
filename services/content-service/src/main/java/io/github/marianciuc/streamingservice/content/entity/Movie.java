package com.mv.streamingservice.content.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@Entity
@Table(name = "movies")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Movie extends BaseEntity{

    @OneToOne(mappedBy = "movie")
    private Content content;

    @OneToMany(mappedBy = "movie")
    List<MediaLink> mediaLink;
}
