/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: V2__.sql
 *
 */

CREATE TABLE media
(
    id             UUID NOT NULL,
    content_type   VARCHAR(255),
    content_length BIGINT,
    author_id      UUID,
    content_id     UUID,
    resolution_id  UUID,
    media_type     VARCHAR(255),
    created_at     TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at     TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    record_status  VARCHAR(30)                 DEFAULT 'ACTIVE',
    data           OID,
    CONSTRAINT pk_media PRIMARY KEY (id)
);

CREATE TABLE resolutions
(
    id          UUID NOT NULL,
    name        VARCHAR(255),
    description VARCHAR(255),
    height      INTEGER,
    width       INTEGER,
    bitrate     INTEGER,
    CONSTRAINT pk_resolutions PRIMARY KEY (id)
);

ALTER TABLE media
    ADD CONSTRAINT FK_MEDIA_ON_RESOLUTION FOREIGN KEY (resolution_id) REFERENCES resolutions (id);