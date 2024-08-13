package com.mv.streamingservice.content.specifications;

import com.mv.streamingservice.content.entity.Content;
import com.mv.streamingservice.content.enums.ContentType;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class ContentSpecification {

    public static Specification<Content> titleContains(String title) {
        return (root, query, cb) -> cb.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<Content> typeIs(ContentType type) {
        return (root, query, cb) -> cb.equal(root.get("type"), type);
    }

    public static Specification<Content> genreIdIs(UUID genreId) {
        return (root, query, cb) -> cb.equal(root.get("genreId"), genreId);
    }
}
