package com.wqm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wqm.entity.PageResult;
import com.wqm.entity.SysRole;
import com.wqm.entity.query.AddRoleQuery;
import com.wqm.entity.query.RoleQuery;
import com.wqm.entity.query.UpdateRoleQuery;
import com.wqm.entity.request.ChangeStatusReq;
import com.wqm.entity.vo.PageVo;
import com.wqm.entity.vo.RoleByIdVo;
import com.wqm.result.ResponseResult;

import java.util.List;

public interface RoleService extends IService<SysRole> {
    List<String> selectRoleKeyByUserId(Long id);

    PageVo roleList(String roleName,String status,Integer pageNum,Integer pageSize);

    void changeStatus(ChangeStatusReq req);

    void addRole(AddRoleQuery query);

    List<SysRole> listAllRole();

    void deleteRole(Long id);

    RoleByIdVo getRoleById(Long id);

    void updateRole(UpdateRoleQuery query);
}
