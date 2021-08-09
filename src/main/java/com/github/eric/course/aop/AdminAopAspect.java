package com.github.eric.course.aop;

import com.github.eric.course.configuration.UserContext;
import com.github.eric.course.model.HttpException;
import com.github.eric.course.model.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class AdminAopAspect {

    @Around("@annotation(com.github.eric.course.annotation.Admin)")
    public Object checkPermission(ProceedingJoinPoint joinPoint) throws Throwable {
        User currentUser = UserContext.getCurrentUser();
        if(currentUser.getRoles()!=null &&
        currentUser.getRoles().stream().anyMatch(role -> "管理员".equals(role.getName()))){
            return joinPoint.proceed();
        }else {
            throw new HttpException(403,"只有管理员才能访问");
        }
    }
}
