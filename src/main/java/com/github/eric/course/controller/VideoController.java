package com.github.eric.course.controller;

import com.github.eric.course.dao.CourseDao;
import com.github.eric.course.dao.VideoDao;
import com.github.eric.course.model.Course;
import com.github.eric.course.model.HttpException;
import com.github.eric.course.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class VideoController {
    @Autowired
    public VideoDao videoDao;

    @Autowired
    public CourseDao courseDao;

    @PatchMapping("/video/{id}")
    public Video updateVideo(@PathVariable("id") Integer id, @RequestBody Video video) {
        Video videoInDb = videoDao.findById(id)
                .orElseThrow(() -> new HttpException(404, "视频不存在！"));
        videoInDb.setName(video.getName());
        videoInDb.setDescription(video.getDescription());

        return videoDao.saveAndFlush(videoInDb);
    }

    @PostMapping("/course/{id}/video")
    public Video createdVideo(@PathVariable("id") Integer id, @RequestBody Video video) {
        check(video);
        Course courseInDb = courseDao.
                findById(id).orElseThrow(() -> new HttpException(404, "课程不存在！"));

        courseInDb.getVideos().add(video);
        courseDao.saveAndFlush(courseInDb);

        return video;
    }

    private void check(Video video) {

    }


    @DeleteMapping("/video/{id}")
    public void deleteVideo(@PathVariable("id") Integer id, HttpServletResponse response) {
        videoDao.deleteById(id);
        response.setStatus(204);
    }

    @GetMapping("/course/{id}/token")
    public void createdVideo(@PathVariable("id") Integer id) {

    }

    @GetMapping("/video/{id}")
    public Video getVideo(@PathVariable("id") Integer id) {
        return videoDao.findById(id)
                .orElseThrow(() -> new HttpException(404, "视频不存在！"));
    }

    @GetMapping("/course/{id}/video")
    public List<Video> getVideoListByCourseId(@PathVariable("id") Integer id) {
        Course courseInDb = courseDao.
                findById(id).orElseThrow(() -> new HttpException(404, "课程不存在！"));

        return courseInDb.getVideos();
    }
}
