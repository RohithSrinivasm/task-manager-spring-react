DROP TABLE IF EXISTS task;

CREATE TABLE task (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    due_date DATE,
    user_id BIGINT NOT NULL
);
