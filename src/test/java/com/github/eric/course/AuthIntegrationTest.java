package com.github.eric.course;


import com.fasterxml.jackson.databind.ObjectMapper;
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

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CourseApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(properties = {"spring.config.location=classpath:test-application.yml"})
public class AuthIntegrationTest {
    @Autowired
    private Environment environment;

    private ObjectMapper objectMapper = new ObjectMapper();

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

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .header("Accept", "application/json")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create("http://localhost:" + getPort() + "/api/v1/user"))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        User user = objectMapper.readValue(httpResponse.body(), User.class);
        Assertions.assertEquals(201, httpResponse.statusCode());
        Assertions.assertEquals("aaa", user.getUsername());
        Assertions.assertNull(user.getEncrypted_password());

        client = HttpClient.newHttpClient();
        request = HttpRequest.newBuilder()
                .header("Accept", "Application/json")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create("http://localhost:" + getPort() + "/api/v1/session"))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        String setCookie = httpResponse.headers().firstValue("Set-Cookie").get();
        user = objectMapper.readValue(httpResponse.body(), User.class);
        Assertions.assertNotNull(setCookie);
        Assertions.assertEquals(200, httpResponse.statusCode());
        Assertions.assertEquals("aaa", user.getUsername());
    }
}
