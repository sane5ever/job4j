CREATE TABLE log
(
    date   TIMESTAMP NOT NULL UNIQUE,
    amount INTEGER   NOT NULL
);

CREATE TABLE vacancy
(
    title       VARCHAR PRIMARY KEY,
    description VARCHAR   NOT NULL,
    link        VARCHAR   NOT NULL,
    date        TIMESTAMP NOT NULL
);

