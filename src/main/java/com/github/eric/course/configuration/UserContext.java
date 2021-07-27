package com.github.eric.course.configuration;

import com.github.eric.course.model.User;

public class UserContext {
    private static ThreadLocal<User> currentUser;

    public static User getCurrentUser() {
        return currentUser.get();
    }

    public static void setCurrentUser(User user) {
        currentUser.set(user);
    }
}
