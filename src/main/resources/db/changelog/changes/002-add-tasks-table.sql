CREATE TABLE tasks (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    description VARCHAR(255),
    status VARCHAR(255),
    due_date TIMESTAMP,
    user_id BIGINT,
    CONSTRAINT fk_tasks_user FOREIGN KEY (user_id) REFERENCES profile_user(id)
);