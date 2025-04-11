package com.wqm.controller;

import com.wqm.entity.LoginUser;
import com.wqm.entity.SysMenu;
import com.wqm.entity.SysUser;
import com.wqm.entity.User;
import com.wqm.entity.vo.AdminUserInfoVo;
import com.wqm.entity.vo.RoutersVo;
import com.wqm.entity.vo.UserInfoVo;
import com.wqm.enums.AppHttpCodeEnum;
import com.wqm.exception.SystemException;
import com.wqm.result.ResponseResult;
import com.wqm.service.LoginService;
import com.wqm.service.MenuService;
import com.wqm.service.RoleService;
import com.wqm.service.SysUserService;
import com.wqm.utils.BeanCopyUtils;
import com.wqm.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class LoginController {
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private LoginService loginService;
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody SysUser user){
        if(!StringUtils.hasText(user.getUserName())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }
    @RequestMapping("/getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        //获取登录用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //获取权限信息
        List<String> perms =  menuService.selectPermsByUserId(loginUser.getUser().getId());
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());
        //获取角色信息
        SysUser user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms,roleKeyList,userInfoVo);

        return ResponseResult.okResult(adminUserInfoVo);
    }
    @GetMapping("/getRouters")
    public ResponseResult<RoutersVo> getRouters(){
        Long userId = SecurityUtils.getUserId();
        //查询menu 结果是tree的形式
        List<SysMenu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        //封装数据返回
        return ResponseResult.okResult(new RoutersVo(menus));
    }
    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }

}
