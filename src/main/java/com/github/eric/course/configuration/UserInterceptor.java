package com.github.eric.course.configuration;

import com.github.eric.course.dao.SessionDao;
import com.github.eric.course.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Stream;

@Component
public class UserInterceptor implements HandlerInterceptor {
    public static String COOKIE_NAME = "COURSE_APP_SESSION_ID";
    private SessionDao sessionDao;

    @Autowired
    public UserInterceptor(SessionDao sessionDao) {
        this.sessionDao = sessionDao;
    }

    public static Optional<String> getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return Optional.empty();
        }
        return Stream.of(cookies).filter(cookie -> cookie.getName().equals(COOKIE_NAME))
                .map(Cookie::getValue)
                .findFirst();
    }

    private boolean isWhitelist(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String method = request.getMethod();
//        List<String> releaseUrl = Arrays.asList(
//                "/api/v1/code",
//                "/api/v1/login",
//                "/api/v1/session",
//                "/api/v1/logout",
//                "/error",
//                "/",
//                "/index.html",
//                "/manifest.json"
//        );
        Map<String, List<String>> releaseMethodAndUrl = new HashMap<>();
        releaseMethodAndUrl.put("GET", Arrays.asList("/api/v1/session"));
        releaseMethodAndUrl.put("POST", Arrays.asList("/api/v1/session", "/api/v1/user"));
        releaseMethodAndUrl.put("DELETE", Arrays.asList("/api/v1/session"));
//        releaseMethodAndUrl.put("GET",Arrays.asList(""));
//        releaseMethodAndUrl.put("GET",Arrays.asList(""));


        if (uri.startsWith("/static/")) {
            return true;
        }
        if (!releaseMethodAndUrl.containsKey(method)) {
            return false;
        }
        return releaseMethodAndUrl.get(method).contains(uri);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        getCookie(request)
                .flatMap(sessionDao::findByCookie)
                .map(Session::getUser)
                .ifPresent(UserContext::setCurrentUser);
        if (UserContext.getCurrentUser() != null || isWhitelist(request)) {
            return true;
        }
        return false;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.setCurrentUser(null);
    }
}
