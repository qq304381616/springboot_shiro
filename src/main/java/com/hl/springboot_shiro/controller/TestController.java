package com.hl.springboot_shiro.controller;

import com.hl.springboot_shiro.domain.User;
import com.hl.springboot_shiro.repository.UserRepository;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiresRoles("admin")
@RequiresPermissions("add")
public class TestController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/test")
    public Object test(){
        List<User> all = userRepository.findAll();
        return all;
    }
}
