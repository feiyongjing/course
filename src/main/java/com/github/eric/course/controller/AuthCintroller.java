package com.github.eric.course.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.github.eric.course.configuration.UserContext;
import com.github.eric.course.configuration.UserInterceptor;
import com.github.eric.course.dao.SessionDao;
import com.github.eric.course.dao.UserDao;
import com.github.eric.course.model.HttpException;
import com.github.eric.course.model.Session;
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
public class AuthCintroller {
    @Autowired
    private SessionDao sessionDao;
    @Autowired
    private UserRoleManagerService userRoleManagerService;

    private BCrypt.Verifyer verify = BCrypt.verifyer();
    @Autowired
    private UserDao userDao;

    @GetMapping("/admin/users")
    public List<User> getAllUsers() {
        return userRoleManagerService.getAllUsers();
    }

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

    @PostMapping("/user")
    public User register(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
        checkParam(username, password);
        User user = new User();
        user.setUsername(username);
        user.setEncrypted_password(BCrypt.withDefaults().hashToString(12, password.toCharArray()));
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

    private void checkParam(@RequestParam String username, @RequestParam String password) {
        if (username == null || username.length() > 8 || password.length() < 1) {
            throw new HttpException(400, "用户名格式不对");
        }
        if (password == null || password.length() < 6 || password.length() > 16) {
            throw new HttpException(400, "密码格式不对");
        }
    }


}
