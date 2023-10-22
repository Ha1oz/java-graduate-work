-- liquibase formatted sql

-- changeset d1m_k0:1

create table user
(
    id         Serial primary key,
    email      varchar not null,
    first_name varchar not null,
    last_name  varchar not null,
    phone      varchar not null,
    reg_date   date    not null,
    city       varchar,
    role       varchar not null
);
create table ad
(
    id          Serial primary key,
    price       integer not null,
    title       varchar not null,
    description varchar not null,
    author      int REFERENCES users (id) on delete cascade
);
create table comment
(
    id        Serial primary key,
    create_at date    not null,
    text      varchar not null,
    author    int REFERENCES users (id) on delete cascade,
    ads_id    int REFERENCES ads (id) on delete cascade
);
create table image
(
    id         Serial primary key,
    file_size  BIGINT  not null,
    media_type varchar not null,
    data       bytea,
    ads_id     int REFERENCES ads (id) on delete cascade,
    author     int REFERENCES users (id) on delete cascade
);