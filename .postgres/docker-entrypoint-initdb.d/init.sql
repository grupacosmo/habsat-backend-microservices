create schema posts;

create table posts.media (
    id               bigserial primary key not null,
    s3_key           varchar(256) not null,
    name_of_file     varchar(256) not null,
    type_of_file     varchar(256) not null,
    size_of_file     bigint not null,
    created_at       timestamp not null,
    updated_at       timestamp
);

create table posts.posts (
    id               bigserial primary key not null,
    thumbnail_id     bigint constraint fk_thumbnail references posts.media(id),
    title            varchar(256) not null,
    slug             varchar(256) not null unique,
    content          text not null,
    email_of_author  varchar(256) not null,
    published_at     timestamp,
    created_at       timestamp not null,
    updated_at       timestamp
);

create schema flights;
create schema users;
create schema emails;
