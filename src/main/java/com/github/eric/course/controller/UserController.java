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
     * @api {get} /api/v1/user 获取用户列表
     * @apiName 获取用户列表
     * @apiGroup 用户管理
     * @apiDescription
     * 管理员才能访问，获取分页的用户信息，支持搜索
     *
     * @apiHeader {String} Accept application/json
     * @apiParam {Number} [pageSize] 每页包含多少个用户
     * @apiParam {Number} [pageNum] 页码，从1开始
     * @apiParam {String} [orderBy] 排序字段，如username/createdAt
     * @apiParam {String} [orderType] 排序方法，Asc/Desc
     *
     * @apiParamExample Request-Example:
     *            GET /api/v1/user?pageSize=10&pageNum=1&orderBy=id&orderType=Desc&search=四
     *
     * @apiSuccess {Number} totalPage 总页数
     * @apiSuccess {Number} pageNum 当前页码，从1开始
     * @apiSuccess {Number} pageSize 每页包含多少个用户
     * @apiSuccess {List[User]} data 用户列表
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "totalPage": 100,
     *       "pageSize": 10,
     *       "pageNum": 1,
     *       "data": [
     *          {
     *             "id": 12345
     *             "username": "李四"
     *             "roles": [
     *                 {
     *                      "name": "管理员"
     *                 }
     *             ]
     *          }
     *       ]
     *      }
     * @apiError 400 Bad request 若请求中包含错误
     * @apiError 401 Unauthorized 若未登录
     * @apiError 403 Forbidden 若没有权限
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 401 Unauthorized
     *     {
     *       "message": "若未登录"
     *     }
     */
    /**
     * @param search 用户名关键字搜索
     * @param pageSize 分页大小
     * @param pageNum 搜索的页码数
     * @param orderBy 指定字段排序
     * @param orderType 排序策略，asc升序、desc降序
     * @return 用户列表
     */
    @GetMapping("/user")
    public PageResponse<User> getAllUsers(@RequestParam(value = "search", required = false) String search,
                                          @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                          @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                          @RequestParam(value = "orderBy", required = false) String orderBy,
                                          @RequestParam(value = "orderType", required = false) String orderType) {
        if (pageSize == null) {
            pageSize = 10;
        }
        if (pageNum == null) {
            pageNum = 1;
        }
        if (orderType != null && orderBy == null) {
            throw new HttpException(400, "缺少orderBy!");
        }

        return userRoleManagerService.getAllUsers(search, pageSize, pageNum, orderBy, orderType == null ? null : Sort.Direction.fromString(orderType));
    }

    /**
     * @api {patch} /api/v1/user 更新用户
     * @apiName 更新用户信息（权限）
     * @apiGroup 用户管理
     * @apiDescription
     *  管理员才能访问，获取分页的用户信息，支持搜索*
     *
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     *
     * @apiParamExample Request-Example:
     *   {
     *       "id": 12345,
     *       "roles": [
     *          {
     *              "name": "管理员"
     *          }
     *       ]
     *   }
     *
     * @apiSuccess {User} user 更新后的用户信息
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *          {
     *              "id": 12345
     *              "username": "zhangsan"
     *              "roles": [
     *                  {
     *                      "name": "管理员"
     *                  }
     *              ]
     *          }
     * @apiError 400 Bad request 若请求中包含错误
     * @apiError 401 Unauthorized 若未登录
     * @apiError 403 Forbidden 若没有权限
     * @apiError 404 Not Found 若用户未找到
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 403 Forbidden
     *     {
     *       "message": "Forbidden"
     *     }
     */
    /**
     *
     * @param id 要修改的用户 id
     * @param user 新的用户数据
     * @return 更新后的用户信息
     */
    @PatchMapping("/user/{id}")
    public User updateUser(@PathVariable Integer id, @RequestBody User user) {
        clear(user);
        return userRoleManagerService.updateUser(id, user);
    }

    /**
     * @api {get} /api/v1/user/{id} 获取指定id的用户
     * @apiName 获取指定id的用户
     * @apiGroup 用户管理
     * @apiDescription
     *  管理员才能访问此接口
     *
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     *
     * @apiParamExample Request-Example:
     *    GET /api/v1/user/1
     * @apiSuccess {User} user 用户信息
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *          {
     *              "id": 12345
     *              "username": "zhangsan"
     *              "roles": [
     *                  {
     *                      "name": "管理员"
     *                  }
     *              ]
     *          }
     * @apiError 400 Bad Request 若请求中包含错误
     * @apiError 401 Unauthorized 若未登录
     * @apiError 403 Forbidden 若没有权限
     * @apiError 404 Not Found 若用户未找到
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 403 Forbidden
     *     {
     *       "message": "Forbidden"
     *     }
     */
    /**
     * @param id 用户ID
     * @return 获得的用户
     */
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
        if (user.getRoles() == null ||
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
