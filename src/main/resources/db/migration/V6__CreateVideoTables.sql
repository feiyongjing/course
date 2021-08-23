CREATE TABLE course
(
    id                  serial PRIMARY KEY,
    name                VARCHAR(100) NOT NULL,
    description         TEXT NOT NULL,
    teacher_name        VARCHAR(50)  NOT NULL,
    teacher_description VARCHAR(50)  NOT NULL,
    price               INT NOT NULL,
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
    description TEXT NOT NULL,
    course_id   INT NOT NULL,
    video_url   varchar(1000) NOT NULL,
    created_at  TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP    NOT NULL DEFAULT now(),
    status      VARCHAR(10)  NOT NULL DEFAULT 'OK'
);

INSERT INTO video(name, description, course_id, video_url)
VALUES ('21天精通C++ - 1', '21天精通C++ 第一集', 1, 'cource-1/第01集.mp4'),
       ('21天精通C++ - 2', '21天精通C++ 第二集', 1, 'cource-1/第02集.mp4'),
       ('21天精通C++ - 3', '21天精通C++ 第三集', 1, 'cource-1/第03集.mp4'),
       ('Java体系课 - 1', 'Java体系课 第一集', 2, 'cource-1/第03集.mp4');

alter
    sequence video_id_seq restart with 5;