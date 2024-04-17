--liquibase formatted sql

--changeset author:fedorbondarev runInTransaction:true failOnError:true
CREATE TABLE files_metadata(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    unique_name VARCHAR(255) NOT NULL UNIQUE DEFAULT encode(gen_random_bytes(32), 'hex')
);
--rollback drop table files_metadata
