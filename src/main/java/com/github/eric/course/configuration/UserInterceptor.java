package com.github.eric.course.configuration;

import com.github.eric.course.dao.SessionDao;
import com.github.eric.course.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Stream;

public class UserInterceptor implements HandlerInterceptor {
    public static String COOKIE_NAME="COURSE_APP_SESSION_ID";
    @Autowired
    private SessionDao sessionDao;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        Stream.of(cookies).filter(cookie -> cookie.getName().equals(COOKIE_NAME))
                .map(Cookie::getValue)
                .findFirst()
                .flatMap(sessionDao::findByCookie)
                .map(Session::getUser)
                .ifPresent(UserContext::setCurrentUser);
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.setCurrentUser(null);
    }
}
