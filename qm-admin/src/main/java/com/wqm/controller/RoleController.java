package com.wqm.controller;

import com.wqm.entity.query.AddRoleQuery;
import com.wqm.entity.query.RoleQuery;
import com.wqm.entity.query.UpdateRoleQuery;
import com.wqm.entity.request.ChangeStatusReq;
import com.wqm.result.ResponseResult;
import com.wqm.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("system/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @GetMapping("list")
    public ResponseResult roleList(@RequestParam(required = false)String roleName,@RequestParam(required = false)String status,Integer pageNum,Integer pageSize){
        return ResponseResult.okResult(roleService.roleList(roleName,status,pageNum,pageSize));
    }
    @PutMapping("changeStatus")
    public ResponseResult changeStatus(@RequestBody ChangeStatusReq req){
        roleService.changeStatus(req);
        return ResponseResult.okResult();
    }
    @PostMapping
    public ResponseResult addRole(@RequestBody AddRoleQuery query){
        roleService.addRole(query);
        return ResponseResult.okResult();
    }
    @GetMapping("listAllRole")
    public ResponseResult listAllRole(){
        return ResponseResult.okResult(roleService.listAllRole());
    }
    @DeleteMapping("{id}")
    public ResponseResult deleteRole(@PathVariable("id") Long id){
        roleService.deleteRole(id);
        return ResponseResult.okResult();
    }
    @GetMapping("{id}")
    public ResponseResult getRoleById(@PathVariable("id") Long id){
        return ResponseResult.okResult(roleService.getRoleById(id));
    }
    @PutMapping
    public ResponseResult updateRole(@RequestBody UpdateRoleQuery query){
        roleService.updateRole(query);
        return ResponseResult.okResult();

    }
}
