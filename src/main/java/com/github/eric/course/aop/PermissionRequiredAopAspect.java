package com.github.eric.course.aop;

import com.github.eric.course.annotation.PermissionRequired;
import com.github.eric.course.configuration.UserContext;
import com.github.eric.course.model.HttpException;
import com.github.eric.course.model.Permission;
import com.github.eric.course.model.Role;
import com.github.eric.course.model.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

@Aspect
@Configuration
public class PermissionRequiredAopAspect {
    @Around("@annotation(com.github.eric.course.annotation.PermissionRequired)")
    public Object checkPermission(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        String[] permissionGroup = signature.getMethod()
                .getAnnotation(PermissionRequired.class).value();

        User currentUser = UserContext.getCurrentUser();
        if(isPermission(currentUser,permissionGroup)){
            return joinPoint.proceed();
        }else {
            throw new HttpException(403,"没有权限");
        }
    }

    private boolean isPermission(User currentUser,String[] permissionGroup){
        return currentUser.getRoles()!=null && checkPermission(currentUser, permissionGroup);
    }

    private boolean checkPermission(User currentUser, String[] permissionGroup) {
        Set<String> userPermissions=new HashSet<>();

//        currentUser.getRoles().stream()
//                .map(Role::getPermissions)
//                .map(permissions -> permissions.stream().map(Permission::getName).collect(Collectors.toSet()))
//                .forEach(rolePermission -> userPermissions.addAll(new ArrayList<>(rolePermission)));


        currentUser.getRoles().stream()
                .map(Role::getPermissions)
                .forEach(permissions -> permissions.stream()
                        .map(Permission::getName).forEach(userPermissions::add));
        return Stream.of(permissionGroup).allMatch(userPermissions::contains);
    }
}
