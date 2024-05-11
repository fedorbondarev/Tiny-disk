--liquibase formatted sql

--changeset author:fedorbondarev runInTransaction:true failOnError:true
CREATE TABLE texts(
    id SERIAL PRIMARY KEY,
    text text NOT NULL
);
--rollback drop table texts
