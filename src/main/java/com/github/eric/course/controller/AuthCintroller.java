package com.github.eric.course.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import ch.qos.logback.classic.spi.EventArgUtil;
import com.github.eric.course.configuration.UserContext;
import com.github.eric.course.dao.SessionDao;
import com.github.eric.course.dao.UserRepository;
import com.github.eric.course.model.HttpException;
import com.github.eric.course.model.Session;
import com.github.eric.course.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static com.github.eric.course.configuration.UserInterceptor.COOKIE_NAME;

@RestController
@RequestMapping("/api/v1")
public class AuthCintroller {
    @Autowired
    private SessionDao sessionDao;
    private BCrypt.Verifyer verify=BCrypt.verifyer();
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/session")
    public Session authStatus() {
        User currentuser = UserContext.getCurrentUser();
        if (currentuser == null) {
            throw new HttpException(401, "Unauthorized");
        }
        Session session = new Session();
        session.setUser(currentuser);
        return session;

    }

    @PostMapping("/session")
    public User login(@RequestParam String username, @RequestParam String password,HttpServletResponse response) {
        checkParam(username, password);

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new HttpException(401, "用户不存在");
        } else {
            if (verify.verify(password.toCharArray(), user.getEncrypted_password()).verified){
                String cookie = UUID.randomUUID().toString();
                response.addCookie(new Cookie(COOKIE_NAME,cookie));
                return user;
            }
            throw new HttpException(401, "用户名密码不匹配");
        }

    }

    //    public User logout(){
//
//    }
    @PostMapping("/user")
    public User register(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
        checkParam(username, password);
        User user = new User();
        user.setUsername(username);
        user.setEncrypted_password(BCrypt.withDefaults().hashToString(12, password.toCharArray()));
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new HttpException(409, "该用户以及注册了");
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
