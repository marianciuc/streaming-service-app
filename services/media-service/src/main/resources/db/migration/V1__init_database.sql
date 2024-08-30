/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: V1__init_database.sql
 *
 */

create table if not exists resolutions
(
    id          UUID primary key,
    name        varchar(255) not null,
    description varchar(255) not null,
    height      integer      not null,
    width       integer      not null,
    bitrate     integer      not null
);

create table if not exists media
(
    id             UUID primary key,
    data           bytea        not null,
    author_id      UUID         not null,
    content_id     UUID         not null,
    content_type   varchar(255) not null,
    media_type     varchar(255) not null,
    content_length bigint       not null,
    created_at     timestamp    not null,
    updated_at     timestamp    not null,
    resolution_id  UUID         not null,
    record_status  varchar(255) not null,
    foreign key (resolution_id) references resolutions (id)
);

