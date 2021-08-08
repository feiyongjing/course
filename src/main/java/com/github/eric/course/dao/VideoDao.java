package com.github.eric.course.dao;

import com.github.eric.course.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoDao extends JpaRepository<Video,Integer> {
}
