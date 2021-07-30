package com.github.eric.course;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.eric.course.model.Session;
import com.github.eric.course.model.User;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.springframework.http.MediaType.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CourseApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(properties = {"spring.config.location=classpath:test-application.yml"})
public class AuthIntegrationTest {
    @Autowired
    private Environment environment;

    private ObjectMapper objectMapper = new ObjectMapper();
    private HttpClient client = HttpClient.newHttpClient();

    @Value("${spring.datasource.url}")
    private String databaseUrl;
    @Value("${spring.datasource.username}")
    private String databaseUsername;
    @Value("${spring.datasource.password}")
    private String databasePassword;

    @BeforeEach
    public void initDatabase() {
        // 在每个测试开始前，执行一次flyway:clean flyway:migrate
        ClassicConfiguration conf = new ClassicConfiguration();
        conf.setDataSource(databaseUrl, databaseUsername, databasePassword);
        Flyway flyway = new Flyway(conf);
        flyway.clean();
        flyway.migrate();
    }

    public String getPort() {
        return environment.getProperty("local.server.port");
    }


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
    @Test
    public void onlyAdminCanSeeAllUsers() throws IOException, InterruptedException {
        HttpResponse<String> response = get("admin/users", "COURSE_APP_SESSION_ID=admin");
        Assertions.assertEquals(200,response.statusCode());
    }
    @Test
    public void nonAdminCanNotSeeAllUsers() throws IOException, InterruptedException {
        HttpResponse<String> response = get("admin/users", "COURSE_APP_SESSION_ID=pupils");
        Assertions.assertEquals(403,response.statusCode());
    }


    private HttpResponse<String> post(String body, String accept, String contentType, String path) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .header("Accept", accept)
                .header("Content-Type", contentType)
                .uri(URI.create("http://localhost:" + getPort() + "/api/v1/" + path))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private HttpResponse<String> get(String path, String cookie) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .header("Accept", APPLICATION_JSON_VALUE)
                .header("Cookie", cookie)
                .uri(URI.create("http://localhost:" + getPort() + "/api/v1/" + path))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private HttpResponse<String> delete(String path, String cookie) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .header("Accept", APPLICATION_JSON_VALUE)
                .header("Cookie", cookie)
                .uri(URI.create("http://localhost:" + getPort() + "/api/v1/" + path))
                .DELETE()
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
