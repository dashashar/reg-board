-- liquibase formatted sql

-- changeset dasha:20250515-init-db
CREATE TABLE IF NOT EXISTS account
(
    id            BIGINT GENERATED ALWAYS AS IDENTITY (START WITH 10000000 INCREMENT BY 5) PRIMARY KEY,
    email         VARCHAR(255) UNIQUE NOT NULL,
    hash_password VARCHAR(255)        NOT NULL,
    first_name    VARCHAR(50)         NOT NULL,
    last_name     VARCHAR(50)         NOT NULL
);

CREATE TABLE IF NOT EXISTS organization
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY (START WITH 10000000 INCREMENT BY 5) PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(500),
    email       VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS organization_account
(
    id              BIGINT GENERATED ALWAYS AS IDENTITY (START WITH 10000000 INCREMENT BY 5) PRIMARY KEY,
    organization_id BIGINT NOT NULL,
    account_id      BIGINT NOT NULL,
    UNIQUE (organization_id, account_id),
    FOREIGN KEY (organization_id) REFERENCES organization (id),
    FOREIGN KEY (account_id) REFERENCES account (id)
);

CREATE TABLE IF NOT EXISTS category_event
(
    id   SMALLSERIAL PRIMARY KEY,
    name VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS event
(
    id              BIGINT GENERATED ALWAYS AS IDENTITY (START WITH 10000000 INCREMENT BY 5) PRIMARY KEY,
    organization_id BIGINT        NOT NULL,
    title           VARCHAR(255)  NOT NULL,
    description     VARCHAR(4000) NOT NULL,
    time_start      TIMESTAMP     NOT NULL,
    time_end        TIMESTAMP     NOT NULL,
    city            VARCHAR(255)  NOT NULL,
    address         VARCHAR(255)  NOT NULL,
    img_id          VARCHAR(36),
    category_id     SMALLINT      NOT NULL,
    CHECK (time_end > time_start),
    FOREIGN KEY (category_id) REFERENCES category_event (id),
    FOREIGN KEY (organization_id) REFERENCES organization (id)
);

CREATE TABLE IF NOT EXISTS ticket
(
    id            BIGINT GENERATED ALWAYS AS IDENTITY (START WITH 10000000 INCREMENT BY 5) PRIMARY KEY,
    event_id      BIGINT       NOT NULL,
    name          VARCHAR(100) NOT NULL,
    price         INTEGER      NOT NULL,
    total_tickets INTEGER      NOT NULL,
    sold_tickets  INTEGER      NOT NULL,
    sales_start   TIMESTAMP    NOT NULL,
    sales_end     TIMESTAMP    NOT NULL,
    CHECK (sales_end > sales_start),
    CHECK (price >= 0),
    FOREIGN KEY (event_id) REFERENCES event (id)
);

CREATE TABLE IF NOT EXISTS registration
(
    id         UUID PRIMARY KEY,
    event_id   BIGINT      NOT NULL,
    account_id BIGINT      NOT NULL,
    status     VARCHAR(15) NOT NULL,
    FOREIGN KEY (event_id) REFERENCES event (id),
    FOREIGN KEY (account_id) REFERENCES account (id)
);

CREATE TABLE IF NOT EXISTS form_field
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY (START WITH 10000000 INCREMENT BY 5) PRIMARY KEY,
    event_id    BIGINT       NOT NULL,
    field_type  VARCHAR(20)  NOT NULL,
    question    VARCHAR(500) NOT NULL,
    is_required BOOLEAN      NOT NULL DEFAULT FALSE,
    position    SMALLINT     NOT NULL,
    options     VARCHAR(4000),
    FOREIGN KEY (event_id) REFERENCES event (id)
);

CREATE TABLE IF NOT EXISTS field_answer
(
    id              BIGINT GENERATED ALWAYS AS IDENTITY (START WITH 10000000 INCREMENT BY 5) PRIMARY KEY,
    registration_id UUID          NOT NULL,
    field_id        BIGINT        NOT NULL,
    answer_value    VARCHAR(1000) NULL,
    UNIQUE (registration_id, field_id),
    FOREIGN KEY (registration_id) REFERENCES registration (id),
    FOREIGN KEY (field_id) REFERENCES form_field (id)
);

