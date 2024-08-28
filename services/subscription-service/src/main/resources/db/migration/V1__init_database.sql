create table if not exists subscriptions
(
    id                      uuid primary key not null,
    name                    varchar(255)     not null unique,
    description             varchar(512)     not null,
    start_date              date             not null,
    end_date                date             not null,
    currency                varchar(20)      not null,
    duration_in_days        integer          not null,
    price                   numeric(19, 4)   not null,
    allowed_active_sessions integer          not null,
    record_status           varchar(50)      not null,
    is_temporary            bool,
    next_subscription_id    uuid references subscriptions (id)
);

create table if not exists resolutions
(
    id          uuid primary key not null,
    name        varchar(5)       not null unique,
    description varchar(255)     not null
);

create table if not exists subscription_resolutions
(
    subscription_id uuid references subscriptions (id),
    resolution_id   uuid references resolutions (id),
    primary key (subscription_id, resolution_id)
);

create table if not exists user_subscriptions
(
    id              uuid primary key not null,
    subscription_id uuid references subscriptions (id),
    user_id         uuid             not null,
    order_id        uuid             not null unique,
    start_date      timestamp        not null default now(),
    end_date        timestamp        not null,
    status          varchar(20)      not null
);