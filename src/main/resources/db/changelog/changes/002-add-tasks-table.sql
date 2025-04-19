CREATE TABLE task (
    id BIGINT PRIMARY KEY,
    title VARCHAR2(255),
    description VARCHAR2(255),
    status VARCHAR2(255),
    due_date TIMESTAMP,
    owner_id BIGINT,
    CONSTRAINT fk_tasks_user FOREIGN KEY (owner_id) REFERENCES profile_user(id)
);

CREATE SEQUENCE task_sequence START WITH 1 INCREMENT BY 1;
ALTER TABLE task ALTER COLUMN id SET DEFAULT nextval('task_sequence');