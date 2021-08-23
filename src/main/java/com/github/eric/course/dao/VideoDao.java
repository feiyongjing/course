package com.github.eric.course.dao;

import com.github.eric.course.model.Status;
import com.github.eric.course.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VideoDao extends JpaRepository<Video,Integer> {
    Optional<Video> findByIdAndStatus(Integer id, Status status);
}
