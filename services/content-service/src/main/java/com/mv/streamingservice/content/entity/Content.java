package com.mv.streamingservice.content.entity;

import com.mv.streamingservice.content.enums.ContentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "content", indexes = {
        @Index(name = "index_content_title", columnList = "title")
})
public class Content extends BaseEntity{
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type")
    private ContentType contentType;

    @Column(name = "release_date", nullable = false)
    private LocalDateTime releaseDate;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "rating")
    private double rating;

    @Column(name = "age_rating")
    private String ageRating;

    @Column(name = "poster_url")
    private String posterUrl;

    @ManyToMany
    @JoinTable(
            name = "content_genres",
            joinColumns = @JoinColumn(name = "content_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private Movie movie;

    @OneToMany(mappedBy = "content")
    private List<Season> seasons;
}
