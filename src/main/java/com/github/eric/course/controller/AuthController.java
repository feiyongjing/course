package com.github.eric.course.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.github.eric.course.configuration.UserContext;
import com.github.eric.course.configuration.UserInterceptor;
import com.github.eric.course.dao.SessionDao;
import com.github.eric.course.dao.UserDao;
import com.github.eric.course.model.HttpException;
import com.github.eric.course.model.Session;
import com.github.eric.course.model.Status;
import com.github.eric.course.model.User;
import com.github.eric.course.service.UserRoleManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

import static com.github.eric.course.configuration.UserInterceptor.COOKIE_NAME;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
    @Autowired
    private SessionDao sessionDao;


    private BCrypt.Verifyer verify = BCrypt.verifyer();
    @Autowired
    private UserDao userDao;



    /**
     * @api {get} /api/v1/session 检查登录状态
     * @apiName 检查登录状态
     * @apiGroup 登录与鉴权
     *
     * @apiHeader {String} Accept application/json
     *
     * @apiParamExample Request-Example:
     *            GET /api/v1/auth
     *
     * @apiSuccess {User} user 用户信息
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "user": {
     *           "id": 123,
     *           "username": "Alice"
     *       }
     *     }
     * @apiError 401 Unauthorized 若用户未登录
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 401 Unauthorized
     *     {
     *       "message": "Unauthorized"
     *     }
     */
    /**
     * @return 已登录的用户
     */
    @GetMapping("/session")
    public Session authStatus() {
        User currentUser = UserContext.getCurrentUser();
        if (currentUser == null) {
            throw new HttpException(401, "用户未登录");
        }
        Session session = new Session();
        session.setUser(currentUser);
        return session;

    }

    /**
     * @api {post} /api/v1/session 登录
     * @apiName 登录
     * @apiGroup 登录与鉴权
     *
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/x-www-form-urlencoded
     *
     * @apiParam {String} username 用户名
     * @apiParam {String} password 密码
     * @apiParamExample Request-Example:
     *          username: Alice
     *          password: MySecretPassword
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 201 Created
     *     {
     *       "user": {
     *           "id": 123,
     *           "username": "Alice"
     *       }
     *     }
     *
     * @apiError 400 Bad Request 若用户的请求包含错误
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 400 Bad Request
     *     {
     *       "message": "Bad Request"
     *     }
     */
    /**
     * @param username 用户名
     * @param password 密码
     */
    @PostMapping("/session")
    public User login(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
        checkParam(username, password);

        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new HttpException(401, "用户不存在");
        } else {
            if (verify.verify(password.toCharArray(), user.getEncrypted_password()).verified) {

                String cookie = UUID.randomUUID().toString();

                Session session = new Session();
                session.setCookie(cookie);
                session.setUser(user);
                sessionDao.save(session);

                response.addCookie(new Cookie(COOKIE_NAME, cookie));

                return user;
            }
            throw new HttpException(401, "用户名密码不匹配");
        }

    }

    /**
     * @api {delete} /api/v1/session 登出
     * @apiName 登出
     * @apiGroup 登录与鉴权
     *
     * @apiHeader {String} Accept application/json
     *
     * @apiParamExample Request-Example:
     *            DELETE /api/v1/session
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 204 No Content
     * @apiError 401 Unauthorized 若用户未登录
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 401 Unauthorized
     *     {
     *       "message": "Unauthorized"
     *     }
     */
    /**
     * @param response Http response
     */
    @DeleteMapping("/session")
    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        if (UserContext.getCurrentUser() == null) {
            throw new HttpException(401, "用户未登录");
        }

        UserInterceptor.getCookie(request).ifPresent(sessionDao::deleteByCookie);
        Cookie cookie = new Cookie(COOKIE_NAME, "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        response.setStatus(204);
    }

    /**
     * @api {post} /api/v1/register 用户注册
     * @apiName 用户注册
     * @apiGroup 登录与鉴权
     *
     * @apiParam {String} username  用户名
     * @apiParam {String} password  密码
     *
     * @apiSuccess {Integer} id 用户id
     * @apiSuccess {string} username 用户名
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 201 Created
     *     {
     *       "id": "123",
     *       "username": "Alice"
     *     }
     *
     * @apiError 400 Bad Request 若用户的请求包含错误
     *
     * @apiErrorExample Error-Response:
     *      HTTP/1.1 400 Bad Request
     *    {
     *      "message": "Bad Request"
     *    }
     *
     * @apiError 409 Conflict 若用户名已经被注册
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 409 Conflict
     *     {
     *       "message": "用户名已经被注册"
     *     }
     */
    /**
     * @param username 用户名
     * @param password 密码
     */
    @PostMapping("/user")
    public User register(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
        checkParam(username, password);
        User user = new User();
        user.setUsername(username);
        user.setEncrypted_password(BCrypt.withDefaults().hashToString(12, password.toCharArray()));
        user.setStatus(Status.OK);
        try {
            userDao.save(user);
        } catch (Throwable e) {
            if (e instanceof DataIntegrityViolationException) {
                throw new HttpException(409, "该用户以及注册了");
            } else {
                throw new RuntimeException(e);
            }
        }
        response.setStatus(201);
        return user;

    }

    private static void checkParam(@RequestParam String username, @RequestParam String password) {
        if (username == null || username.length() > 8 || password.length() < 1) {
            throw new HttpException(400, "用户名格式不对");
        }
        if (password == null || password.length() < 6 || password.length() > 16) {
            throw new HttpException(400, "密码格式不对");
        }
    }


}
