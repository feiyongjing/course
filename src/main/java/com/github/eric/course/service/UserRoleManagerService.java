package com.github.eric.course.service;

import com.github.eric.course.annotation.Admin;
import com.github.eric.course.dao.UserDao;
import com.github.eric.course.model.PageResponse;
import com.github.eric.course.model.User;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleManagerService {
    @Autowired
    UserDao userDao;

    @Admin
    public PageResponse<User> getAllUsers(Integer pageSize, Integer pageNum, Integer orderBy, Integer orderType) {

        long count = userDao.count();
        long totalPage= count%pageSize==0 ? count/pageSize : count/pageSize+1;



        return userDao.getAllUsers()
    }

    @Admin
    public User updateUser(User user) {
        return userDao.saveAndFlush(user);
    }
    @Admin
    public User getUserById(Integer userId) {
        return userDao.getById(userId);
    }
}
