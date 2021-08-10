package com.github.eric.course.service;

import com.github.eric.course.annotation.PermissionRequired;
import com.github.eric.course.dao.CourseDao;
import com.github.eric.course.dao.VideoDao;
import com.github.eric.course.model.Course;
import com.github.eric.course.model.HttpException;
import com.github.eric.course.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class VideoService {

    @Autowired
    public VideoDao videoDao;

    @Autowired
    public CourseDao courseDao;

    @PermissionRequired(value = {"课程管理"})
    public Video updateVideo(Integer id, Video video) {
        Video videoInDb = videoDao.findById(id)
                .orElseThrow(() -> new HttpException(404, "视频不存在！"));
        videoInDb.setName(video.getName());
        videoInDb.setDescription(video.getDescription());

        return videoDao.saveAndFlush(videoInDb);
    }

    @PermissionRequired(value = {"课程管理"})
    public Video createdVideo(Integer id, Video video) {
        Course courseInDb = courseDao.
                findById(id).orElseThrow(() -> new HttpException(404, "课程不存在！"));

        courseInDb.getVideos().add(video);
        courseDao.saveAndFlush(courseInDb);
        return video;
    }

    @PermissionRequired(value = {"课程管理"})
    public void deleteVideoByVideoId(Integer id, HttpServletResponse response) {
        videoDao.deleteById(id);
        response.setStatus(204);
    }

    public Video getVideoByVideoId(Integer id) {
        return videoDao.findById(id)
                .orElseThrow(() -> new HttpException(404, "视频不存在！"));
    }

    public List<Video> getVideoListByCourseId(Integer id) {
        Course courseInDb = courseDao.
                findById(id).orElseThrow(() -> new HttpException(404, "课程不存在！"));

        return courseInDb.getVideos();
    }
}
