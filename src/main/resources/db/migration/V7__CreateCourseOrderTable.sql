CREATE TABLE course_order
(
    id         serial PRIMARY KEY,
    course_id  INT,
    user_id    INT,
    price      INT, -- 单位是分
    created_at TIMESTAMP   NOT NULL DEFAULT now(),
    updated_at TIMESTAMP   NOT NULL DEFAULT now(),
    -- UNPAID, PAID, DELETED
    status     VARCHAR(10) NOT NULL DEFAULT 'UNPAID'
);
INSERT INTO course_order(course_id, user_id, price,status)
VALUES (1,1,99,'PAID'),
       (2,1,100,'UNPAID');
alter
    sequence course_order_id_seq restart with 2;