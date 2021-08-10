package com.github.eric.course.controller;

import com.github.eric.course.dao.CourseDao;
import com.github.eric.course.dao.VideoDao;
import com.github.eric.course.model.Course;
import com.github.eric.course.model.HttpException;
import com.github.eric.course.model.Video;
import com.github.eric.course.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class VideoController {
    @Autowired
    public VideoService videoService;

    @PatchMapping("/video/{id}")
    public Video updateVideo(@PathVariable("id") Integer id, @RequestBody Video video) {
        return videoService.updateVideo(id,video);
    }

    @PostMapping("/course/{id}/video")
    public Video createdVideo(@PathVariable("id") Integer id, @RequestBody Video video) {
        check(video);
        return videoService.createdVideo(id,video);
    }

    private void check(Video video) {

    }


    @DeleteMapping("/course/{id}/video")
    public void deleteVideo(@PathVariable("id") Integer id, HttpServletResponse response) {
        videoService.deleteVideoByVideoId(id,response);
    }

    @GetMapping("/course/{id}/token")
    public void createdVideo(@PathVariable("id") Integer id) {

    }

    @GetMapping("/video/{id}")
    public Video getVideo(@PathVariable("id") Integer id) {
        return videoService.getVideoByVideoId(id);
    }

    @GetMapping("/course/{id}/video")
    public List<Video> getVideoListByCourseId(@PathVariable("id") Integer id) {

        return videoService.getVideoListByCourseId(id);
    }
}
