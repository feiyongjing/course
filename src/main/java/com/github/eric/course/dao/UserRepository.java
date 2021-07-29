package com.github.eric.course.dao;

import com.github.eric.course.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {
//    @Query(value = "select * from user where username=:username and encrypted_password=:encryptPs",nativeQuery = true)
    User findByUsername(String username);
}
