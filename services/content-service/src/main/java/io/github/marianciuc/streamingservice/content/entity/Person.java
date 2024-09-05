/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: Person.java
 *
 */

package io.github.marianciuc.streamingservice.content.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Person extends BaseEntity{

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "biography", nullable = false)
    private String biography;

    @NotNull
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @NotNull
    @Column(name = "birth_place", nullable = false)
    private String birthPlace;

    @Column(name = "image_url")
    private String imageUrl;
}
