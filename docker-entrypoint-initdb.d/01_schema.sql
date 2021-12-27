CREATE TABLE characters
(
    id               SERIAL PRIMARY KEY,
    nickname         TEXT        NOT NULL,
    name             TEXT                 DEFAULT '',
    description      TEXT        NOT NULL,
    image            TEXT        NOT NULL,
    id_comics        INTEGER[]    NOT NULL,
    current_time_now timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE comics
(
    id               SERIAL PRIMARY KEY,
    title            TEXT        NOT NULL,
    issue_number     INT         NOT NULL CHECK ( issue_number > 0),
    writer           TEXT[]      NOT NULL,
    published        TEXT        NOT NULL,
    description      TEXT        NOT NULL,
    image            TEXT        NOT NULL,
    id_characters    INTEGER[]    NOT NULL,
    current_time_now timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
);