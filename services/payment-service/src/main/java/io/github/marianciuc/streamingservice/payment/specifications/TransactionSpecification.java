package io.github.marianciuc.streamingservice.payment.specifications;

import io.github.marianciuc.streamingservice.payment.entity.Transaction;
import io.github.marianciuc.streamingservice.payment.enums.PaymentStatus;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

/**
 * The ContentSpecification class provides static methods for constructing
 * specifications for querying Content objects based on specific criteria.
 * These specifications can be used with the Spring Data JPA Specifications API.
 */
public class TransactionSpecification {

    public static Specification<Transaction> hasStatus(PaymentStatus status) {
        return (Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (status == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    public static Specification<Transaction> hasUserId(UUID userId) {
        return (Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            if (userId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("userId"), userId);
        };
    }

    public static Specification<Transaction> buildSpec(PaymentStatus status, UUID userId) {
        return Specification.where(hasStatus(status))
                .and(hasUserId(userId));
    }
}
