package com.wqm.controller;

import com.wqm.entity.SysUser;
import com.wqm.entity.User;
import com.wqm.result.ResponseResult;
import com.wqm.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin

public class BlogLoginController {
    @Autowired
    private BlogLoginService loginService;
    @PostMapping("/login")
    public ResponseResult login(@RequestBody SysUser user){ return loginService.login(user);}
    @PostMapping("/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }

}
