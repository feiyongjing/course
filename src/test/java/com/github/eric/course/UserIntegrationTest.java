package com.github.eric.course;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.eric.course.model.PageResponse;
import com.github.eric.course.model.Role;
import com.github.eric.course.model.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserIntegrationTest extends AbstractIntegrationTest {
    @Test
    public void get401IfNotLogIn() throws IOException, InterruptedException {
        assertEquals(401, get("/user").statusCode());
        assertEquals(401, get("/user?search=a&pageSize=10&pageNum=1").statusCode());
        assertEquals(401, get("/user/1").statusCode());
    }

    @Test
    public void get403IfNotAdmin() throws IOException, InterruptedException {
        User user = new User();
        Role role = new Role();
        role.setName("管理员");
        user.setRoles(Set.of(role));
        assertEquals(403, get("/user", pupilsUserCookie).statusCode());
        assertEquals(403, get("/user/1", pupilsUserCookie).statusCode());
        assertEquals(403, get("/user", teacherUserCookie).statusCode());
        assertEquals(403, get("/user/1", teacherUserCookie).statusCode());
        assertEquals(403, patch("/user/1", objectMapper.writeValueAsString(user), Map.of("Cookie", teacherUserCookie)).statusCode());
        assertEquals(403, patch("/user/1", objectMapper.writeValueAsString(user), Map.of("Cookie", pupilsUserCookie)).statusCode());
    }

    @Test
    public void get404IfUserNotExist() throws IOException, InterruptedException {
        assertEquals(404, get("/user/0", adminUserCookie).statusCode());
    }
    @Test
    public void adminGetUserById() throws IOException, InterruptedException {
        HttpResponse<String> response = get("/user/1", adminUserCookie);
        User user = objectMapper.readValue(response.body(), User.class);
        assertEquals(200, response.statusCode());
        assertEquals("张三", user.getUsername());
        assertEquals(Collections.singletonList("学生"),
                user.getRoles().stream().map(Role::getName).collect(toList()));
    }

    @Test
    public void adminGetAllUserBySearchAndOrder() throws IOException, InterruptedException {
        HttpResponse<String> response = get("/user?pageSize=10&pageNum=1&orderBy=username&orderType=Desc&search=四", adminUserCookie);
        PageResponse<User> pageResponse = objectMapper.readValue(response.body(), new TypeReference<>() {
        });
        assertEquals(200, response.statusCode());
        assertEquals(1, pageResponse.getTotalPage());
        assertEquals(1, pageResponse.getPageNum());
        assertEquals(10, pageResponse.getPageSize());
        assertEquals(Collections.singletonList("李四"),
                pageResponse.getDate().stream().map(User::getUsername).collect(toList()));
        assertEquals(Collections.singletonList(Collections.singletonList("教师")),
                pageResponse.getDate().stream()
                        .map(User::getRoles)
                        .map(roles -> roles.stream().map(Role::getName).collect(toList()))
                        .collect(toList()));

    }

    @Test
    public void adminUpdateUserRolesByUserId() throws IOException, InterruptedException {
        // 使用用户1带有管理员权限Cookie查看用户2的角色信息，将查询的角色信息替换为管理员
        String responseBody = get("/user/2", adminUserCookie).body();
        User user = objectMapper.readValue(responseBody, User.class);
        Role role = new Role();
        role.setName("管理员");
        user.getRoles().add(role);

        // 按照上述替换后的信息使用管理员权限的Cookie修改用户2的角色信息
        var response = patch("/user/2", objectMapper.writeValueAsString(user), Map.of("Cookie", adminUserCookie));
        assertEquals(200, response.statusCode());
        user = objectMapper.readValue(response.body(), User.class);
        assertEquals("李四", user.getUsername());
        assertEquals(Set.of("教师", "管理员"), user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));

        // 现在用户2也是管理员了，使用用户2带有管理员权限的Cookie访问只有管理员权限访问的数据
        assertEquals(200, get("/user/1", teacherUserCookie).statusCode());
    }
}
