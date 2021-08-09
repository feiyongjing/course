package com.github.eric.course;


import com.github.eric.course.model.Course;
import com.github.eric.course.model.Video;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseIntegrationTest extends AbstractIntegrationTest {
    @Test
    public void createCourseSuccessTest() throws IOException, InterruptedException {
        Course course = new Course();
        course.setName("Java体系课");
        course.setDescription("包含了Java基础和Java项目实战课");
        course.setTeacher_name("李四");
        course.setTeacher_description("15年Java开发经验，担任过多个项目架构师");
        course.setPrice(new BigDecimal(10000));

        HttpResponse<String> response = post("/course", objectMapper.writeValueAsString(course), Map.of("Cookie", teacherUserCookie));
        assertEquals(200, response.statusCode());
        Course courseInDb = objectMapper.readValue(response.body(), Course.class);
        assertEquals(course.getName(), courseInDb.getName());
        assertEquals(course.getDescription(),courseInDb.getDescription());
        assertEquals(course.getTeacher_name(),courseInDb.getTeacher_name());
        assertEquals(course.getTeacher_description(),courseInDb.getTeacher_description());
        assertEquals(course.getPrice(),courseInDb.getPrice());

    }

    @Test
    public void getAndUpdateCourseSuccessTest() throws IOException, InterruptedException {

        // 查看指定id的课程
        HttpResponse<String> response = get("/course/1",pupilsUserCookie);
        assertEquals(200, response.statusCode());
        Course courseInDb = objectMapper.readValue(response.body(), Course.class);
        assertEquals("21天精通C++", courseInDb.getName());
        assertEquals("让你21天精通C++",courseInDb.getDescription());
        assertEquals("Torvalds Linus",courseInDb.getTeacher_name());
        assertEquals("Creator of Linux",courseInDb.getTeacher_description());
        assertEquals(new BigDecimal(10000),courseInDb.getPrice());
        assertEquals(Arrays.asList("21天精通C++ - 1","21天精通C++ - 2","21天精通C++ - 3"),
                courseInDb.getVideos().stream().map(Video::getName).collect(Collectors.toList()));
        assertEquals(Arrays.asList("21天精通C++ 第一集","21天精通C++ 第二集","21天精通C++ 第三集"),
                courseInDb.getVideos().stream().map(Video::getDescription).collect(Collectors.toList()));

        // 制造修改的课程数据
        Course course=new Course();
        course.setName("前端体系课");
        course.setDescription("包含了HTML、Css、JavaScript基础");
        course.setTeacher_name("李四");
        course.setTeacher_description("某知名大厂员工");
        course.setPrice(new BigDecimal(6666));

        // 对指定id课程进行修改
        response = patch("/course/1",objectMapper.writeValueAsString(course),Map.of("Cookie", teacherUserCookie));
        assertEquals(200, response.statusCode());
        courseInDb = objectMapper.readValue(response.body(), Course.class);
        assertEquals(course.getName(), courseInDb.getName());
        assertEquals(course.getDescription(),courseInDb.getDescription());
        assertEquals(course.getTeacher_name(),courseInDb.getTeacher_name());
        assertEquals(course.getTeacher_description(),courseInDb.getTeacher_description());
        assertEquals(course.getPrice(),courseInDb.getPrice());

        // 再次查看指定id的课程，是修改后的课程数据
        response = get("/course/1",pupilsUserCookie);
        assertEquals(200, response.statusCode());
        courseInDb = objectMapper.readValue(response.body(), Course.class);
        assertEquals(course.getName(), courseInDb.getName());
        assertEquals(course.getDescription(),courseInDb.getDescription());
        assertEquals(course.getTeacher_name(),courseInDb.getTeacher_name());
        assertEquals(course.getTeacher_description(),courseInDb.getTeacher_description());
        assertEquals(course.getPrice(),courseInDb.getPrice());

    }

    @Test
    public void deleteCourseSuccessTest() throws IOException, InterruptedException {
        HttpResponse<String> response = delete("/course?id=1",teacherUserCookie);
        assertEquals(204, response.statusCode());

        response = get("/course/1",pupilsUserCookie);
        assertEquals(404, response.statusCode());
    }
}
