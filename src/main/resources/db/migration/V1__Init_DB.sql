CREATE TABLE IF NOT EXISTS friend_service.friendship(
    id bigint NOT NULL,
    status_id bigint NOT NULL,
    src_person_id bigint NOT NULL,
    dst_person_id bigint NOT NULL,
    CONSTRAINT friendship_pkey PRIMARY KEY(id)
);

CREATE TYPE code AS ENUM ('REQUEST', 'FRIEND', 'BLOCKED', 'DECLINED', 'SUBSCRIBED');

CREATE TABLE IF NOT EXISTS friend_service.friendship_status(
    id bigint NOT NULL,
    "time" date NOT NULL,
    name text COLLATE pg_catalog."default" NOT NULL,
    friendship_status code NOT NULL,
    CONSTRAINT friendship_status_pkey PRIMARY KEY(id)
);