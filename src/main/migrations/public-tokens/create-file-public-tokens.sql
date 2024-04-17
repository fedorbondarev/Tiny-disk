--liquibase formatted sql

--changeset author:fedorbondarev runInTransaction:true failOnError:true
CREATE TABLE file_public_tokens(
    id SERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE DEFAULT encode(gen_random_bytes(32), 'hex'),
    file_metadata_id bigint NOT NULL REFERENCES files_metadata(id)
);
--rollback drop table public_token_to_file
