package io.github.marianciuc.streamingservice.content.specifications;

import io.github.marianciuc.streamingservice.content.entity.Content;
import io.github.marianciuc.streamingservice.content.enums.ContentType;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

/**
 * The ContentSpecification class provides static methods for constructing
 * specifications for querying Content objects based on specific criteria.
 * These specifications can be used with the Spring Data JPA Specifications API.
 */
public class ContentSpecification {

    /**
     * Returns a Specification that checks if the title of a Content object contains the specified title.
     *
     * @param title the title to search for in the Content objects
     * @return a Specification that checks if the title of a Content object contains the specified title
     */
    public static Specification<Content> titleContains(String title) {
        return (root, query, cb) -> cb.like(root.get("title"), "%" + title + "%");
    }

    /**
     * Returns a Specification that checks if the type of a Content object is equal to the specified type.
     *
     * @param type the ContentType to check for in the Content objects
     * @return a Specification that checks if the type of a Content object is equal to the specified type
     */
    public static Specification<Content> typeIs(ContentType type) {
        return (root, query, cb) -> cb.equal(root.get("type"), type);
    }

    /**
     * Returns a Specification that checks if the genreId of a Content object is equal to the specified genreId.
     *
     * @param genreId the genreId to check for in the Content objects
     * @return a Specification that checks if the genreId of a Content object is equal to the specified genreId
     */
    public static Specification<Content> genreIdIs(UUID genreId) {
        return (root, query, cb) -> cb.equal(root.get("genreId"), genreId);
    }
}
