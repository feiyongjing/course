package com.github.eric.course;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.eric.course.model.PageResponse;
import com.github.eric.course.model.Role;
import com.github.eric.course.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.stream.Collectors;

public class UserIntegrationTest extends AbstractIntegrationTest {
    @Test
    public void adminGetUserById() throws IOException, InterruptedException {
        HttpResponse<String> response = get("/user/1", "COURSE_APP_SESSION_ID=admin");
        User user = objectMapper.readValue(response.body(), User.class);
        Assertions.assertEquals(200,response.statusCode());
        Assertions.assertEquals("张三",user.getUsername());
        Assertions.assertEquals(Collections.singletonList("学生"),
                user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
    }
    @Test
    public void adminGetAllUserBySearchAndOrder() throws IOException, InterruptedException {
        HttpResponse<String> response = get("/user?pageSize=10&pageNum=1&orderBy=price&orderType=Desc&search=四", "COURSE_APP_SESSION_ID=admin");
        PageResponse<User> pageResponse = objectMapper.readValue(response.body(), new TypeReference<>() {
        });
        Assertions.assertEquals(200,response.statusCode());
        Assertions.assertEquals(1,pageResponse.getTotalPage());
        Assertions.assertEquals(1,pageResponse.getPageNum());
        Assertions.assertEquals(10,pageResponse.getPageSize());
        Assertions.assertEquals(Collections.singletonList("李四"),
                pageResponse.getDate().stream().map(User::getUsername).collect(Collectors.toList()));
        Assertions.assertEquals(Collections.singletonList(Collections.singletonList("教师")),
                pageResponse.getDate().stream()
                        .map(User::getRoles)
                        .map(roles -> roles.stream().map(Role::getName).collect(Collectors.toList()))
                        .collect(Collectors.toList()));

    }
    @Test
    public void adminUpdateUserRolesByUserId() throws IOException, InterruptedException {
//        HttpResponse<String> response = get("/user", "COURSE_APP_SESSION_ID=admin");
//        User user = objectMapper.readValue(response.body(), User.class);
//        Assertions.assertEquals(200,response.statusCode());
//        Assertions.assertEquals("张三",user.getUsername());
//        Assertions.assertEquals(Collections.singletonList("学生"),
//                user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
    }
}
