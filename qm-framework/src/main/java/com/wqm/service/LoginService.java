package com.wqm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wqm.entity.SysUser;
import com.wqm.result.ResponseResult;

public interface LoginService  {
    ResponseResult login(SysUser user);

    ResponseResult logout();
}
