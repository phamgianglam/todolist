CREATE TABLE task (
    id BIGINT PRIMARY KEY,
    title VARCHAR(255),
    description VARCHAR(255),
    status VARCHAR(255),
    due_date TIMESTAMP,
    user_id BIGINT,
    CONSTRAINT fk_tasks_user FOREIGN KEY (user_id) REFERENCES profile_user(id)
);

CREATE SEQUENCE task_sequence START WITH 1 INCREMENT BY 1;
ALTER TABLE task ALTER COLUMN id SET DEFAULT nextval('task_sequence');