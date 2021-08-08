package com.github.eric.course.dao;


import com.github.eric.course.model.Course;
import com.github.eric.course.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CourseDao extends JpaRepository<Course, Integer> {
    @Query(value = "select u from Course u where u.username like %:search%")
    Page<Course> findBySearch(String search, Pageable pageable);
}