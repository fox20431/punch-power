-- INIT

-- DDL Data Definition Language
DROP TABLE IF EXISTS posts;
create table posts(
                      id serial PRIMARY KEY,
                      title varchar(255),
                      body text,
                      created_at timestamp
);

DROP TABLE IF EXISTS comments;
CREATE TABLE comments(
                         id serial primary key,
                         name varchar(255),
                         email varchar(255),
                         content text,
                         created_at timestamp
)

-- DML Data Manipulation Language