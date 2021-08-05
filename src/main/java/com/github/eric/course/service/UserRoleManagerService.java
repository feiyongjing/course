package com.github.eric.course.service;

import com.github.eric.course.annotation.Admin;
import com.github.eric.course.dao.RoleDao;
import com.github.eric.course.dao.UserDao;
import com.github.eric.course.model.HttpException;
import com.github.eric.course.model.PageResponse;
import com.github.eric.course.model.Role;
import com.github.eric.course.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Service
public class UserRoleManagerService {
    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    @Admin
    public PageResponse<User> getAllUsers(String search, Integer pageSize, Integer pageNum, String field, Sort.Direction orderType) {
        Pageable pageable = orderType == null ?
                PageRequest.of(pageNum - 1, pageSize) :
                PageRequest.of(pageNum - 1, pageSize, Sort.by(orderType, field));
        Page<User> page = ObjectUtils.isEmpty(search) ? userDao.findAll(pageable) : userDao.findBySearch(search, pageable);
        return new PageResponse<>(page.getTotalPages(), pageSize, pageNum, page.toList());
    }

    @Admin
    public User updateUser(Integer id,User user) {
        User userInDb = findById(id);

        Map<String, Role> nameToRoles = roleDao.findAll().stream().collect(toMap(Role::getName, x -> x));
        List<Role> newRoles = user.getRoles().stream()
                .map(Role::getName)
                .map(nameToRoles::get)
                .filter(Objects::nonNull)
                .collect(toList());

        userInDb.setRoles(newRoles);
        userDao.save(userInDb);
        return userInDb;
    }
    @Admin
    public User getUserById(Integer userId) {
        return findById(userId);
    }
    private User findById(Integer id) {
        return userDao.findById(id).orElseThrow(() -> new HttpException(404, "用户不存在！"));
    }

}
