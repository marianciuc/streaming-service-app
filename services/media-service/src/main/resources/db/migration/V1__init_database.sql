create table if not exists resolutions
(
    id          uuid primary key,
    name        varchar(255) not null,
    description varchar(255) not null,
    height      integer      not null,
    width       integer      not null,
    bitrate     integer      not null
);

create table if not exists images
(
    id uuid primary key,

)
