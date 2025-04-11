package com.wqm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wqm.entity.SysUser;
import com.wqm.entity.query.AddUserQuery;
import com.wqm.entity.query.ChangeStatusQuery;
import com.wqm.entity.query.UpdateUserQuery;
import com.wqm.entity.vo.PageVo;
import com.wqm.entity.vo.UserRoleVo;
import com.wqm.result.ResponseResult;


/**
 * 用户表(SysUser)表服务接口
 *
 * @author makejava
 * @since 2024-12-20 17:01:31
 */
public interface SysUserService extends IService<SysUser> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(SysUser user);

    ResponseResult register(SysUser user);

    PageVo getUserList(String userName, String phonenumber, Integer pageNum, Integer pageSize);

    void addUser(AddUserQuery query);

    void deleteUser(Long id);

    UserRoleVo getUserById(Long id);

    void updateUser(UpdateUserQuery userQuery);

    void changeStatus(ChangeStatusQuery query);
}
