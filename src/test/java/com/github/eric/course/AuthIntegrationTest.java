package com.github.eric.course;


import com.github.eric.course.model.Session;
import com.github.eric.course.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpResponse;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


public class AuthIntegrationTest extends AbstractIntegrationTest{

    @Test
    public void registerLoginLogout() throws IOException, InterruptedException {
        String body = "username=aaa&password=1234567";

        //用户注册
        HttpResponse<String> httpResponse = post(body, APPLICATION_JSON_VALUE, APPLICATION_FORM_URLENCODED_VALUE, "user");
        User user = objectMapper.readValue(httpResponse.body(), User.class);
        Assertions.assertEquals(201, httpResponse.statusCode());
        Assertions.assertEquals("aaa", user.getUsername());
        Assertions.assertNull(user.getEncrypted_password());
        //用户登录
        httpResponse = post(body, APPLICATION_JSON_VALUE, APPLICATION_FORM_URLENCODED_VALUE, "session");
        String setCookie = httpResponse.headers().firstValue("Set-Cookie").get();
        user = objectMapper.readValue(httpResponse.body(), User.class);
        Assertions.assertNotNull(setCookie);
        Assertions.assertEquals(200, httpResponse.statusCode());
        Assertions.assertEquals("aaa", user.getUsername());
        //查询用户登录状态
        httpResponse = get("session", setCookie);
        Session session = objectMapper.readValue(httpResponse.body(), Session.class);
        Assertions.assertEquals(200, httpResponse.statusCode());
        Assertions.assertEquals("aaa", session.getUser().getUsername());
        //退出登录
        httpResponse = delete("session", setCookie);
        Assertions.assertEquals(204, httpResponse.statusCode());
        //查询用户登录状态
        httpResponse = get("session", setCookie);
        Assertions.assertEquals(401, httpResponse.statusCode());

    }

    @Test
    public void getErrorIfUsernameAlreadyRegistered() throws IOException, InterruptedException {
        String body = "username=aaa&password=1234567";
        HttpResponse<String> httpResponse = post(body, APPLICATION_JSON_VALUE, APPLICATION_FORM_URLENCODED_VALUE, "user");
        User user = objectMapper.readValue(httpResponse.body(), User.class);
        Assertions.assertEquals(201, httpResponse.statusCode());
        Assertions.assertEquals("aaa", user.getUsername());
        Assertions.assertNull(user.getEncrypted_password());

        httpResponse = post(body, APPLICATION_JSON_VALUE, APPLICATION_FORM_URLENCODED_VALUE, "user");
        Assertions.assertEquals(409, httpResponse.statusCode());
    }
//    @Test
//    public void onlyAdminCanSeeAllUsers() throws IOException, InterruptedException {
//        HttpResponse<String> response = get("admin/users", "COURSE_APP_SESSION_ID=admin");
//        Assertions.assertEquals(200,response.statusCode());
//    }
//    @Test
//    public void nonAdminCanNotSeeAllUsers() throws IOException, InterruptedException {
//        HttpResponse<String> response = get("admin/users", "COURSE_APP_SESSION_ID=pupils");
//        Assertions.assertEquals(403,response.statusCode());
//    }



}
