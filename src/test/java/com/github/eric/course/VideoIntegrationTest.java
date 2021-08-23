package com.github.eric.course;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.eric.course.model.Video;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VideoIntegrationTest extends AbstractIntegrationTest {
    @Test
    public void createdVideoSuccessTest() throws IOException, InterruptedException {
        Video video=new Video("21天精通C++ - 4", "21天精通C++ 第四集");
        video.setVideo_url("cource-1/第03集.mp4");
        HttpResponse<String> response =post("/course/1/video",objectMapper.writeValueAsString(video), Map.of("Cookie", teacherUserCookie));
        assertEquals(200, response.statusCode());

        Video videoInDb = objectMapper.readValue(response.body(), Video.class);
        assertEquals(video.getName(), videoInDb.getName());
        assertEquals(video.getDescription(),videoInDb.getDescription());

    }

    @Test
    public void getAndUpdateVideoSuccessTest() throws IOException, InterruptedException {
        // 获取指定id的视频信息
        HttpResponse<String> response =get("/video/1",pupilsUserCookie);
        assertEquals(200, response.statusCode());
        Video videoInDb = objectMapper.readValue(response.body(), Video.class);
        assertEquals("21天精通C++ - 1", videoInDb.getName());
        assertEquals("21天精通C++ 第一集",videoInDb.getDescription());

        // 修改指定id的视频信息
        Video video=new Video("21天精通C++ - 1-1", "第一集");
        response =patch("/video/1",objectMapper.writeValueAsString(video), Map.of("Cookie", teacherUserCookie));
        assertEquals(200, response.statusCode());
        videoInDb = objectMapper.readValue(response.body(), Video.class);
        assertEquals(video.getName(), videoInDb.getName());
        assertEquals(video.getDescription(),videoInDb.getDescription());

        // 再次获取指定id的视频信息
        response =get("/video/1",pupilsUserCookie);
        assertEquals(200, response.statusCode());
        videoInDb = objectMapper.readValue(response.body(), Video.class);
        assertEquals(video.getName(), videoInDb.getName());
        assertEquals(video.getDescription(),videoInDb.getDescription());
    }

    @Test
    public void deleteVideoSuccessTest() throws IOException, InterruptedException {
        // 获取指定id的视频信息
        HttpResponse<String> response =get("/video/1",pupilsUserCookie);
        assertEquals(200, response.statusCode());
        Video videoInDb = objectMapper.readValue(response.body(), Video.class);
        assertEquals("21天精通C++ - 1", videoInDb.getName());
        assertEquals("21天精通C++ 第一集",videoInDb.getDescription());

        response =delete("/course/1/video",teacherUserCookie);
        assertEquals(204, response.statusCode());

        // 再次获取指定id的视频信息
        response =get("/video/1",pupilsUserCookie);
        assertEquals(404, response.statusCode());
    }

    @Test
    public void getVideoListSuccessTest() throws IOException, InterruptedException {

        HttpResponse<String> response = get("/course/1/video",pupilsUserCookie);
        assertEquals(200, response.statusCode());
        List<Video> videoInDb = objectMapper.readValue(response.body(), new TypeReference<>() {});
        assertEquals(Arrays.asList("21天精通C++ - 1","21天精通C++ - 2","21天精通C++ - 3"),
                videoInDb.stream().map(Video::getName).collect(Collectors.toList()));
        assertEquals(Arrays.asList("21天精通C++ 第一集","21天精通C++ 第二集","21天精通C++ 第三集"),
                videoInDb.stream().map(Video::getDescription).collect(Collectors.toList()));

    }
}
