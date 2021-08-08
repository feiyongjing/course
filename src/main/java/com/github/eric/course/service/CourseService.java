package com.github.eric.course.service;

import com.github.eric.course.dao.CourseDao;
import com.github.eric.course.model.Course;
import com.github.eric.course.model.PageResponse;
import com.github.eric.course.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
}
