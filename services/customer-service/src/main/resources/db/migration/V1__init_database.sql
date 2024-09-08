create table if not exists customers
(
    id uuid unique not null primary key,
    email varchar(200) unique not null,
    username varchar(200) unique not null,
    theme varchar(200) not null,
    profile_picture varchar(200) not null,
    preferred_language varchar(50) not null,
    is_email_verified boolean not null,
    profile_is_completed boolean not null,
    receive_newsletter boolean not null,
    birth_date date not null,
    country varchar(200) not null,
    enable_notifications boolean not null,
    created_at timestamp,
    updated_at timestamp
);

create index if not exists idxEmail on customers (email);
create index if not exists idxUsername on customers (username);