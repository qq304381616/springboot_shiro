package com.hl.springboot_shiro.controller;

import com.hl.springboot_shiro.domain.User;
import com.hl.springboot_shiro.repository.UserRepository;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public String login(String username, String password) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return "参数错误";
        }

        User user = userRepository.findByUsernameAndPassword(username, password);
        if (user == null) {
            return "用户名或密码错误";
        }

        SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password));

        return "登录成功";
    }

    @PostMapping("/unLogin")
    public String unLogin() {
        return "未登录";
    }

    @PostMapping("/info")
    public String info() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return user.getUsername();
    }
}
