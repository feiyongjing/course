package com.github.eric.course.controller;

import com.github.eric.course.dao.CourseDao;
import com.github.eric.course.model.Course;
import com.github.eric.course.model.HttpException;
import com.github.eric.course.model.PageResponse;
import com.github.eric.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1")
public class CourseController {

    @Autowired
    public CourseDao courseDao;

    @Autowired
    public CourseService courseService;

    @PatchMapping("/course/{id}")
    public Course updateCourse(@PathVariable("id") Integer id, @RequestBody Course course) {
        return courseService.updateCourse(id,course);
    }

    @PostMapping("/course")
    public Course createdCourse(@RequestBody Course course) {
        check(course);
        return courseService.createdCourse(course);
    }

    private void check(Course course) {

    }

    @DeleteMapping("/course")
    public void deleteCourse(@RequestParam("id") Integer id, HttpServletResponse response) {
        courseService.deleteCourse(id,response);
    }

    @GetMapping("/course/{id}")
    public Course getCourseById(@PathVariable("id") Integer id) {
        return courseService.getCourseById(id);
    }

    @GetMapping("/course")
    public PageResponse<Course> getCourse(@RequestParam(value = "search", required = false) String search,
                                  @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                  @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                  @RequestParam(value = "orderBy", required = false) String orderBy,
                                  @RequestParam(value = "orderType", required = false) String orderType) {
        if (pageSize == null) {
            pageSize = 10;
        }
        if (pageNum == null) {
            pageNum = 1;
        }
        if (orderType != null && orderBy == null) {
            throw new HttpException(400, "缺少orderBy!");
        }
        return courseService.getUserAllCourse(search, pageSize, pageNum, orderBy, orderType == null ? null : Sort.Direction.fromString(orderType));
    }


}
