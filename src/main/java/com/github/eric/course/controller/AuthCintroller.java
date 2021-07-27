package com.github.eric.course.controller;

import com.github.eric.course.configuration.UserContext;
import com.github.eric.course.dao.SessionDao;
import com.github.eric.course.model.HttpException;
import com.github.eric.course.model.Session;
import com.github.eric.course.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/api/vi")
public class AuthCintroller {
    @Autowired
    private SessionDao sessionDao;

    @GetMapping("/session")
    public Session authStatus(){
        User currentuser= UserContext.getCurrentUser();
        if (currentuser==null){
            throw new HttpException(401,"用户未登录");
        }
        Session session=new Session();
        session.setUser(currentuser);
        return session;

    }
//    public User login(){
//
//    }
//    public User logout(){
//
//    }
//    public User register(){
//
//    }



}
