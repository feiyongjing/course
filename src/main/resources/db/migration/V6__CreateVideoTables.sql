CREATE TABLE course
(
    id                  serial PRIMARY KEY,
    name                VARCHAR(100) NOT NULL,
    description         TEXT,
    teacher_name        VARCHAR(50)  NOT NULL,
    teacher_description VARCHAR(50)  NOT NULL,
    price               INT,
    created_at          TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at          TIMESTAMP    NOT NULL DEFAULT now(),
    status              VARCHAR(10)  NOT NULL DEFAULT 'OK'
);

INSERT INTO course(id, name, description, teacher_name, teacher_description, price)
VALUES (1, '21天精通C++', '让你21天精通C++', 'Torvalds Linus', 'Creator of Linux', 10000),
       (2, 'Java体系课', '从零开始学习Java', 'james gosling', 'Creator of Java', 8888),
       (3, '前端体系课', '包含Html、Css、JavaScript基础', 'tim berners-lee', 'Creator of the World Wide Web', 9999);

alter
    sequence course_id_seq restart with 4;

CREATE TABLE video
(
    id          serial PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    description TEXT,
    course_id   INT,
    video_url   varchar(1000),
    created_at  TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP    NOT NULL DEFAULT now(),
    status      VARCHAR(10)  NOT NULL DEFAULT 'OK'
);

INSERT INTO video(name, description, course_id)
VALUES ('21天精通C++ - 1', '21天精通C++ 第一集', 1),
       ('21天精通C++ - 2', '21天精通C++ 第二集', 1),
       ('21天精通C++ - 3', '21天精通C++ 第三集', 1);

alter
    sequence video_id_seq restart with 4;