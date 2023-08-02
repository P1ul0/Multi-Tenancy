CREATE SCHEMA honda;
CREATE SCHEMA toyota;

CREATE TABLE honda.car(
     id      UUID PRIMARY KEY,
     name    VARCHAR(255) NOT NULL,
     color    VARCHAR(255),
     brand    VARCHAR(255)
);

CREATE TABLE toyota.car(
     id      UUID PRIMARY KEY,
     name    VARCHAR(255) NOT NULL,
     color    VARCHAR(255),
     brand    VARCHAR(255)
);