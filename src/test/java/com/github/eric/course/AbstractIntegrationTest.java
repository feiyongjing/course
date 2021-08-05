package com.github.eric.course;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.junit.jupiter.api.BeforeEach;
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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CourseApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(properties = {"spring.config.location=classpath:test-application.yml"})
public class AbstractIntegrationTest {
    @Autowired
    public Environment environment;

    public ObjectMapper objectMapper = new ObjectMapper();
    public HttpClient client = HttpClient.newHttpClient();

    @Value("${spring.datasource.url}")
    public String databaseUrl;
    @Value("${spring.datasource.username}")
    public String databaseUsername;
    @Value("${spring.datasource.password}")
    public String databasePassword;

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
    public HttpResponse<String> post(String body, String accept, String contentType, String path) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .header("Accept", accept)
                .header("Content-Type", contentType)
                .uri(URI.create("http://localhost:" + getPort() + "/api/v1/" + path))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> get(String path, String cookie) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .header("Accept", APPLICATION_JSON_VALUE)
                .header("Cookie", cookie)
                .uri(URI.create("http://localhost:" + getPort() + "/api/v1/" + path))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> delete(String path, String cookie) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .header("Accept", APPLICATION_JSON_VALUE)
                .header("Cookie", cookie)
                .uri(URI.create("http://localhost:" + getPort() + "/api/v1/" + path))
                .DELETE()
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
