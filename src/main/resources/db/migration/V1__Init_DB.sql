CREATE TABLE IF NOT EXISTS friendship(
    id                         BIGSERIAL,
    status_id                  BIGINT NOT NULL,
    src_person_id              BIGINT NOT NULL,
    dst_person_id              BIGINT NOT NULL,
    CONSTRAINT friendship_pkey PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS friendship_status(
    id                          BIGSERIAL,
    "time"                      DATE NOT NULL,
    name                        TEXT COLLATE pg_catalog."default" NOT NULL,
    status_code                 TEXT NOT NULL,
    CONSTRAINT friendship_status_pkey PRIMARY KEY(id)
);
