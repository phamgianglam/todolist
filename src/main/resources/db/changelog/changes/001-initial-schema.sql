CREATE TABLE profile_user (
    id BIGINT PRIMARY KEY,
    username VARCHAR2(255) NOT NULL,
    password VARCHAR2(255) NOT NULL,
    email VARCHAR2(255) NOT NULL,
    role VARCHAR2(255) DEFAULT 'USER'
);
CREATE SEQUENCE user_sequence START WITH 1 INCREMENT BY 1;
ALTER TABLE profile_user ALTER COLUMN id SET DEFAULT nextval('user_sequence');