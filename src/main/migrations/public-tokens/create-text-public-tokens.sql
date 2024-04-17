--liquibase formatted sql

--changeset author:fedorbondarev runInTransaction:true failOnError:true
CREATE TABLE texts_public_tokens(
    id SERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE DEFAULT encode(gen_random_bytes(32), 'hex'),
    text_id bigint NOT NULL REFERENCES texts(id)
);
--rollback drop table public_tokens_to_texts
