package com.github.eric.course.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody UsernameAndPassword usernameAndPassword){
        String username= usernameAndPassword.getUsername();
        String password=usernameAndPassword.getPassword();
        return "";
    }
    class UsernameAndPassword{
        private String Username;
        private String Password;

        public String getUsername() {
            return Username;
        }

        public void setUsername(String username) {
            Username = username;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String password) {
            Password = password;
        }
    }
}
