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
    content_type varchar(255),
    content_length bigint,
    file_name varchar(255),
    created_at timestamp
);

create table if not exists video
(
    id uuid primary key,
    content_id uuid,
    content_type varchar(255),
    media_type varchar(255),
    processed_resolutions integer,
    status varchar(255),
    master_playlist_path varchar(255)
);

create table if not exists files
(
    id uuid primary key,
    video_id uuid references video(id),
    resolution_id uuid references resolutions(id),
    is_processed boolean,
    play_list_path varchar(255)
);

create table if not exists video_uploading_statues
(
    id uuid primary key,
    title varchar(200),
    message varchar(200),
    video_id uuid references video(id),
    type varchar(200)
);
