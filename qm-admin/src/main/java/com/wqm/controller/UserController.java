package com.wqm.controller;

import com.wqm.entity.query.AddUserQuery;
import com.wqm.entity.query.ChangeStatusQuery;
import com.wqm.entity.query.UpdateUserQuery;
import com.wqm.entity.request.ChangeStatusReq;
import com.wqm.entity.vo.UserRoleVo;
import com.wqm.result.ResponseResult;
import com.wqm.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("system/user")
public class UserController {
    @Autowired
    private SysUserService userService;
    private UserRoleVo UserRoleVo;

    @GetMapping("/list")
    public ResponseResult getUserList(@RequestParam(required = false) String userName,@RequestParam(required = false) String phonenumber, Integer pageNum, Integer pageSize){
        return ResponseResult.okResult(userService.getUserList(userName,phonenumber,pageNum,pageSize));
    }
    @PostMapping
    public ResponseResult addUser(@RequestBody AddUserQuery query){
        userService.addUser(query);
        return ResponseResult.okResult();
    }
    @DeleteMapping("{id}")
    public ResponseResult deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return ResponseResult.okResult();
    }
    @GetMapping("{id}")
    public ResponseResult getUserById(@PathVariable("id") Long id){
        return  ResponseResult.okResult(userService.getUserById(id));
    }
    @PutMapping
    public  ResponseResult updateUser(@RequestBody UpdateUserQuery query){
        userService.updateUser(query);
        return ResponseResult.okResult();
    }
    @PutMapping("changeStatus")
    public ResponseResult changeStatus(@RequestBody ChangeStatusQuery query){
        userService.changeStatus(query);
        return ResponseResult.okResult();
    }
}
