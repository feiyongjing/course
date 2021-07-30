package com.github.eric.course.dao;

import com.github.eric.course.model.Session;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SessionDao extends CrudRepository<Session,Integer> {
    Optional<Session> findByCookie(String cookie);

    void deleteByCookie(String cookie);
}
