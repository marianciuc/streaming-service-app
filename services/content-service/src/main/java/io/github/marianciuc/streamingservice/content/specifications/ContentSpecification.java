package io.github.marianciuc.streamingservice.content.specifications;

import io.github.marianciuc.streamingservice.content.entity.Content;
import io.github.marianciuc.streamingservice.content.enums.ContentType;
import io.github.marianciuc.streamingservice.content.enums.RecordStatus;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

/**
 * The ContentSpecification class provides static methods for constructing
 * specifications for querying Content objects based on specific criteria.
 * These specifications can be used with the Spring Data JPA Specifications API.
 */
public class ContentSpecification {

    public static Specification<Content> titleContains(String title) {
        return (root, query, cb) -> cb.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<Content> typeIs(ContentType type) {
        return (root, query, cb) -> cb.equal(root.get("type"), type);
    }

    public static Specification<Content> hasGenre(UUID genreId) {
        return (root, query, cb) -> cb.equal(root.join("genres").get("id"), genreId);
    }

    public static Specification<Content> hasDirector(UUID directorId) {
        return (root, query, cb) -> cb.equal(root.join("directors").get("id"), directorId);
    }

    public static Specification<Content> actorIdIs(UUID actorId) {
        return (root, query, cb) -> cb.equal(root.join("actors").get("id"), actorId);
    }

    public static Specification<Content> ageRatingIs(String ageRating) {
        return (root, query, cb) -> cb.equal(root.get("ageRating"), ageRating);
    }

    public static Specification<Content> releaseDateYearIs(String releaseDateYear) {
        return (root, query, cb) -> cb.equal(cb.function("YEAR", Integer.class, root.get("releaseDate")), Integer.parseInt(releaseDateYear));
    }

    public static Specification<Content> recordStatusIs(RecordStatus recordStatus) {
        return (root, query, cb) -> cb.equal(root.get("recordStatus"), recordStatus);
    }

    public static Specification<Content> orderByReleaseDateDesc() {
        return (root, query, cb) -> {
            query.orderBy(cb.desc(root.get("releaseDate")));
            return cb.conjunction();
        };
    }
}
