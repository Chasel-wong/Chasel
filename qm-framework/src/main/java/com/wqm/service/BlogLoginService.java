package com.wqm.service;

import com.wqm.entity.SysUser;
import com.wqm.entity.User;
import com.wqm.result.ResponseResult;
import org.springframework.stereotype.Service;


public interface BlogLoginService {
    ResponseResult login(SysUser user);

    ResponseResult logout();
}
