package com.github.eric.course.dao;

import com.github.eric.course.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDao extends JpaRepository<User,Integer> {
//    @Query(value = "select * from user where username=:username and encrypted_password=:encryptPs",nativeQuery = true)
    User findByUsername(String username);

    @Query(value = "select u from User u where u.username like %:search%")
    Page<User> findBySearch(@Param("search") String search, Pageable pageable);

}
