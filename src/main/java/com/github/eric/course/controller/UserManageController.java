package com.github.eric.course.controller;

import com.github.eric.course.model.HttpException;
import com.github.eric.course.model.PageResponse;
import com.github.eric.course.model.Role;
import com.github.eric.course.model.User;
import com.github.eric.course.service.UserRoleManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class UserManageController {
    @Autowired
    private UserRoleManagerService userRoleManagerService;

    /**
     * 查看所有的用户，需要管理员权限
     *
     * @return 所有的用户
     */
    @GetMapping("/user")
    public PageResponse<User> getAllUsers(@RequestParam("pagesize") Integer pageSize,
                                   @RequestParam("pagenum") Integer pageNum,
                                   @RequestParam("orderby") Integer orderBy,
                                   @RequestParam("ordertype") Integer orderType) {
        return userRoleManagerService.getAllUsers(pageSize,pageNum,orderBy,orderType);
    }

    /**
     * 更新用户权限，需要管理员权限
     * @param user
     * @return
     */
    @PatchMapping("/user")
    public User updateUser(@RequestBody User user) {
        clear(user);
        return userRoleManagerService.updateUser(user);
    }

    /**
     * 查看指定id的用户，需要管理员权限
     *
     * @return 用户信息
     */
    @GetMapping("/user")
    public User getUserById(@PathParam("id") Integer userId) {
        if(userId==null){
            throw new HttpException(400,"Bad request");
        }
        return userRoleManagerService.getUserById(userId);
    }

    public void clear(User user) {
        if (user.getId() == null ||
                user.getRoles() == null ||
                user.getRoles().size() == 0 ||
                !user.getRoles().stream().allMatch(this::checkRoles)
        ) {
            throw new HttpException(400,"Bad request");
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
