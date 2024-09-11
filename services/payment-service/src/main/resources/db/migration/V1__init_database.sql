create table if not exists transactions
(
    id                       uuid unique not null primary key,
    order_id                 uuid        not null,
    currency                 varchar(3)  not null,
    payment_method_id        uuid,
    cardholder_id            uuid,
    stripe_payment_intent_id varchar(100),
    amount                   bigint      not null,
    failure_message          text,
    status                   varchar(20) not null,
    created_at               timestamp
);

create table if not exists refunds
(
    id               uuid unique not null primary key,
    amount           bigint      not null,
    currency         varchar(3)  not null,
    stripe_refund_id varchar(100),
    status           varchar(20) not null,
    transaction_id   uuid        not null,
    created_at       timestamp
);

create table if not exists addresses
(
    id          uuid unique  not null primary key,
    line1       varchar(100) not null,
    line2       varchar(100),
    city        varchar(100) not null,
    state       varchar(100) not null,
    postal_code varchar(20)  not null,
    country     varchar(100) not null
);

create table if not exists cardholders
(
    id                 uuid unique  not null primary key,
    address_id         uuid         not null,
    stripe_customer_id varchar(100) not null,
    email              varchar(100) not null,
    card_holder_name   varchar(100) not null,
    card_status        varchar(20)  not null,
    phone              varchar(20)  not null
);

alter table transactions
    add constraint fk_transactions_cardholders
        foreign key (cardholder_id) references cardholders (id);

alter table refunds
    add constraint fk_refunds_transactions
        foreign key (transaction_id) references transactions (id);

alter table cardholders
    add constraint fk_cardholders_addresses
        foreign key (address_id) references addresses (id);


