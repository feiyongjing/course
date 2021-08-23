package com.github.eric.course.service;

import com.github.eric.course.annotation.PermissionRequired;
import com.github.eric.course.dao.CourseDao;
import com.github.eric.course.model.Course;
import com.github.eric.course.model.HttpException;
import com.github.eric.course.model.PageResponse;
import com.github.eric.course.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletResponse;

@Service
public class CourseService {
    @Autowired
    public CourseDao courseDao;

    public PageResponse<Course> getUserAllCourse(String search, Integer pageSize, Integer pageNum, String field, Sort.Direction orderType) {
        Pageable pageable = orderType == null ?
                PageRequest.of(pageNum - 1, pageSize) :
                PageRequest.of(pageNum - 1, pageSize, Sort.by(orderType, field));
        Page<Course> page = ObjectUtils.isEmpty(search) ? courseDao.findAll(pageable) : courseDao.findBySearch(search, pageable);
        return new PageResponse<>(page.getTotalPages(), pageSize, pageNum, page.toList());
    }

    @PermissionRequired(value = {"课程管理"})
    public Course updateCourse(Integer id, Course course) {
        Course courseInDb = courseDao.findById(id)
                .orElseThrow(() -> new HttpException(404, "课程不存在！"));
        courseInDb.setName(course.getName());
        courseInDb.setDescription(course.getDescription());
        courseInDb.setTeacher_name(course.getTeacher_name());
        courseInDb.setTeacher_description(course.getTeacher_description());
        courseInDb.setPrice(course.getPrice());

        return courseDao.saveAndFlush(courseInDb);
    }
    @PermissionRequired(value = {"课程管理"})
    public Course createdCourse(Course course) {
        return courseDao.save(course);
    }

    @PermissionRequired(value = {"课程管理"})
    public void deleteCourse(Integer id, HttpServletResponse response) {
        Course courseInDb = courseDao.findById(id)
                .orElseThrow(() -> new HttpException(404, "课程不存在！"));
        courseInDb.setStatus(Status.DELETED);
        courseDao.saveAndFlush(courseInDb);
        response.setStatus(204);
    }

    public Course getCourseById(Integer courseId) {
        return courseDao.findByIdAndStatus(courseId, Status.OK).orElseThrow(() -> new HttpException(404, "课程不存在！"));
    }
}
