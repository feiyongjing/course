package com.github.eric.course.service;

import com.github.eric.course.annotation.Admin;
import com.github.eric.course.dao.UserDao;
import com.github.eric.course.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleManagerService {
    @Autowired
    UserDao userDao;

    @Admin
    public List<User> getAllUsers() {
        return userDao.findAll();
    }
}
