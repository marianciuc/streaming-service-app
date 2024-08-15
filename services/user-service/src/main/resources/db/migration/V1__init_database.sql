create table if not exists users
(
    id uuid unique not null primary key,
    email varchar(200) unique not null,
    username varchar(200) unique not null,
    password_hash text not null,
    role varchar(50) not null,
    user_type varchar(50) not null,
    created_at timestamp,
    updated_at timestamp,
    is_banned boolean not null default false,
    record_status varchar(50) not null
);

create index if not exists idxEmail on users (email);
create index if not exists idxUsername on users (username);