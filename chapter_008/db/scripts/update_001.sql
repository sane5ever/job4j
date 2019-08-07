CREATE SEQUENCE seq START WITH 100000;

CREATE TABLE items
(
    id          INTEGER PRIMARY KEY DEFAULT nextval('seq'),
    name        VARCHAR                           NOT NULL,
    description VARCHAR                           NOT NULL,
    created     TIMESTAMP           DEFAULT now() NOT NULL
);