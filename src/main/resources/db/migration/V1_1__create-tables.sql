CREATE TABLE IF NOT EXISTS user
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255)          NULL,
    status VARCHAR(40),
    created_at date,
    updated_at date,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS file
(
    id        BIGINT AUTO_INCREMENT,
    file_name VARCHAR(255) NOT NULL,
    file_path VARCHAR(255) NOT NULL,
    CONSTRAINT pk_file PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS event
(
    id      BIGINT AUTO_INCREMENT,
    user_id BIGINT,
    file_id BIGINT,
    type    VARCHAR(30),
    CONSTRAINT pk_event PRIMARY KEY (id),
    CONSTRAINT FK_EVENT_ON_FILE FOREIGN KEY (file_id) REFERENCES file (id),
    CONSTRAINT FK_EVENT_ON_USER FOREIGN KEY (user_id) REFERENCES user (id)
);
