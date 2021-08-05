package com.github.eric.course.controller;

import com.github.eric.course.model.HttpException;
import com.github.eric.course.model.PageResponse;
import com.github.eric.course.model.Role;
import com.github.eric.course.model.User;
import com.github.eric.course.service.UserRoleManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserRoleManagerService userRoleManagerService;

    /**
     * 查看所有的用户，需要管理员权限
     *
     * @return 所有的用户
     */
    @GetMapping("/user")
    public PageResponse<User> getAllUsers(@RequestParam(value = "search", required = false) String search,
                                          @RequestParam(value = "pagesize", required = false) Integer pageSize,
                                          @RequestParam(value = "pagenum", required = false) Integer pageNum,
                                          @RequestParam(value = "orderby", required = false) String orderBy,
                                          @RequestParam(value = "ordertype", required = false) String orderType) {
        if (pageSize == null) {
            pageSize = 10;
        }
        if (pageNum == null) {
            pageNum = 1;
        }
        if (orderType != null && orderBy == null) {
            throw new HttpException(400, "缺少orderBy!");
        }

        return userRoleManagerService.getAllUsers(search,pageSize, pageNum, orderBy, orderType == null ? null : Sort.Direction.fromString(orderType));
    }

    /**
     * 更新用户权限，需要管理员权限
     *
     * @param user
     * @return
     */
    @PatchMapping("/user/{id}")
    public User updateUser(@PathVariable Integer id,@RequestBody User user) {
        clear(user);
        return userRoleManagerService.updateUser(id,user);
    }

    /**
     * 查看指定id的用户，需要管理员权限
     *
     * @return 用户信息
     */
    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable("id") Integer userId) {
        if (userId == null) {
            throw new HttpException(400, "Bad request");
        }
        return userRoleManagerService.getUserById(userId);
    }

    public void clear(User user) {
        if (user.getId() == null ||
                user.getRoles() == null ||
                user.getRoles().size() == 0 ||
                !user.getRoles().stream().allMatch(this::checkRoles)
        ) {
            throw new HttpException(400, "Bad request");
        }
    }

    public boolean checkRoles(Role role) {
        List<String> standardRoleName = Arrays.asList("管理员", "教师", "学生");
        if (role == null) {
            return false;
        }
        return standardRoleName.stream().anyMatch(roleName -> roleName.equals(role.getName()));
    }

}
