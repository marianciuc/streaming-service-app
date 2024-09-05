/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CustomerSpecification.java
 *
 */

package io.github.marianciuc.streamingservice.customer.specifications;

import io.github.marianciuc.streamingservice.customer.model.Customer;
import org.springframework.data.jpa.domain.Specification;

public class CustomerSpecification {

    public static Specification<Customer> hasCountry(String country) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("country"), country);
    }

    public static Specification<Customer> usernameStartsWith(String prefix) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("username"), prefix + "%");
    }

    public static Specification<Customer> emailStartsWith(String prefix) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("email"), prefix + "%");
    }

    public static Specification<Customer> isEmailVerified(boolean isEmailVerified) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isEmailVerified"), isEmailVerified);
    }

    public static Specification<Customer> receiveNewsletter(boolean receiveNewsletter) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("receiveNewsletter"), receiveNewsletter);
    }

    public static Specification<Customer> idStartsWith(String prefix) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("id"), prefix + "%");
    }
}
