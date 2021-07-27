package com.github.eric.course.dao;

import com.github.eric.course.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

public interface UserRepository extends CrudRepository<User,Integer> {
}
