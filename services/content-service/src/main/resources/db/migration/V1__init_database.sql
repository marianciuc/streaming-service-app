create table if not exists content
(
    id            uuid         not null primary key,
    created_at    timestamp default (CURRENT_TIMESTAMP),
    updated_at    timestamp default (CURRENT_TIMESTAMP),
    record_status varchar(20)  not null,
    title         varchar(255) not null,
    description   text         not null,
    content_type  varchar(50)  not null,
    release_date  timestamp,
    duration      int          not null,
    rating        double precision,
    age_rating    text,
    poster_url    text,
    movie_id      uuid
);

create table if not exists movies
(
    id            uuid        not null primary key,
    created_at    timestamp default (CURRENT_TIMESTAMP),
    updated_at    timestamp default (CURRENT_TIMESTAMP),
    record_status varchar(20) not null,
    content_id    uuid
);

create table if not exists genres
(
    id            uuid         not null primary key,
    created_at    timestamp default (CURRENT_TIMESTAMP),
    updated_at    timestamp default (CURRENT_TIMESTAMP),
    record_status varchar(20)  not null,
    name          varchar(255) not null,
    description   text         not null
);

create table if not exists content_genres
(
    content_id uuid,
    genre_id   uuid,
    primary key (content_id, genre_id),
    foreign key (content_id) references content (id),
    foreign key (genre_id) references genres (id)
);

create table if not exists episodes
(
    id             uuid        not null primary key,
    created_at     timestamp default (CURRENT_TIMESTAMP),
    updated_at     timestamp default (CURRENT_TIMESTAMP),
    record_status  varchar(20) not null,
    episode_number int         not null,
    title          text        not null,
    release_date   timestamp   not null,
    description    text        not null,
    season_id      uuid not null
);

create table if not exists media_links
(
    id            uuid         not null primary key,
    created_at    timestamp default (CURRENT_TIMESTAMP),
    updated_at    timestamp default (CURRENT_TIMESTAMP),
    record_status varchar(20)  not null,
    link          text         not null,
    resolution    varchar(200) not null,
    movie_id      uuid,
    episode_id    uuid
);

create table if not exists seasons
(
    id            uuid         not null primary key,
    created_at    timestamp default (CURRENT_TIMESTAMP),
    updated_at    timestamp default (CURRENT_TIMESTAMP),
    record_status varchar(20)  not null,
    content_id uuid not null,
    season_number int not null,
    season_title text not null,
    season_release_date  timestamp not null,
    foreign key (content_id) references content(id)
);


alter table content
    add foreign key (movie_id) references movies (id);
alter table movies
    add foreign key (content_id) references content (id);
alter table media_links
    add foreign key (episode_id) references episodes (id),
    add foreign key (movie_id) references movies (id);
alter table episodes add foreign key (season_id) references seasons(id)