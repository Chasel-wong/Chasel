package com.wqm.controller;

import com.wqm.annotation.SystemLog;
import com.wqm.entity.SysUser;
import com.wqm.result.ResponseResult;
import com.wqm.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Autowired
    private SysUserService userService;
    @GetMapping("/userInfo")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }
    @PutMapping("/userInfo")
    @SystemLog(businessName = "更新用户信息")
    public ResponseResult updateUserInfo(@RequestBody SysUser user){
        return userService.updateUserInfo(user);
    }
    @PostMapping("register")
    public ResponseResult register(@RequestBody SysUser user){
        return userService.register(user);
    }
}
