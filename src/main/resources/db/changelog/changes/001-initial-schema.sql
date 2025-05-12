CREATE TABLE profile_user (
    id BIGINT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);
CREATE SEQUENCE user_sequence START WITH 1 INCREMENT BY 1;
ALTER TABLE profile_user ALTER COLUMN id SET DEFAULT nextval('user_sequence');