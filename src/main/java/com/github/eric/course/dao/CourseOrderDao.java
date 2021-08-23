package com.github.eric.course.dao;

import com.github.eric.course.model.CourseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CourseOrderDao extends JpaRepository<CourseOrder,Integer> , JpaSpecificationExecutor<CourseOrder> {
    Optional<CourseOrder> findByCourseIdAndUserId(Integer courseId, Integer userId);
}
