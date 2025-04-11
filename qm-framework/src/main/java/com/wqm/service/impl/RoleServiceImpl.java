package com.wqm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wqm.entity.SysRole;
import com.wqm.entity.SysRoleMenu;
import com.wqm.entity.query.AddRoleQuery;
import com.wqm.entity.query.RoleQuery;
import com.wqm.entity.query.UpdateRoleQuery;
import com.wqm.entity.request.ChangeStatusReq;
import com.wqm.entity.vo.PageVo;
import com.wqm.entity.vo.RoleByIdVo;
import com.wqm.entity.vo.RoleVo;
import com.wqm.mapper.RoleMenuMapper;
import com.wqm.mapper.SysRoleMapper;
import com.wqm.result.ResponseResult;
import com.wqm.service.RoleService;
import com.wqm.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements RoleService {
    @Autowired
    private SysRoleMapper roleMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Override
    public List<String> selectRoleKeyByUserId(Long id) {

        //判断是否是管理员 如果是返回集合中只需要有admin
        if(id == 1L){
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //否则查询用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public PageVo roleList(String roleName,String status,Integer pageNum,Integer pageSize) {
        Long count = roleMapper.selectBackRoleCount(roleName, status);
        if (count==0){
            return new PageVo();
        }
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
       wrapper.eq(SysRole::getDelFlag,"0");
//               .like(SysRole::getStatus, status)
//               .like(SysRole::getRoleName,roleName);
        Page<SysRole> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(pageNum);
        page(page, wrapper);
        List<SysRole> records = page.getRecords();
        return new PageVo(page.getRecords(),page.getTotal());
    }

    @Override
    public void changeStatus(ChangeStatusReq req) {
        SysRole sysRole = baseMapper.selectOne(new LambdaQueryWrapper<SysRole>().eq(SysRole::getId, req.getRoleId()));
        sysRole.setStatus(req.getStatus());
        baseMapper.updateById(sysRole);
    }

    @Override
    public void addRole(AddRoleQuery query) {
        SysRole existRole = baseMapper.selectOne(new LambdaQueryWrapper<SysRole>().select(SysRole::getId).eq(SysRole::getRoleName, query.getRoleName()));
        Assert.isNull(existRole, query.getRoleName() + "角色名已存在");
        SysRole role = BeanCopyUtils.copyBean(query, SysRole.class);
        baseMapper.insert(role);
    }

    @Override
    public List<SysRole> listAllRole() {
        List<SysRole> roles = baseMapper.selectList(new LambdaQueryWrapper<SysRole>().eq(SysRole::getStatus, "0"));
        return roles;
    }

    @Override
    public void deleteRole(Long id) {
        baseMapper.deleteById(id);
    }

    @Override
    public RoleByIdVo getRoleById(Long id) {
        SysRole role = baseMapper.selectById(id);
        RoleByIdVo vo = BeanCopyUtils.copyBean(role, RoleByIdVo.class);
        return vo;
    }

    @Override
    public void updateRole(UpdateRoleQuery query) {
        SysRole role = BeanCopyUtils.copyBean(query, SysRole.class);
        baseMapper.updateById(role);
        roleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId,query.getId()));
        for (Long id:query.getMenuIds()){
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setRoleId(query.getId());
            roleMenu.setMenuId(id);
            roleMenuMapper.insert(roleMenu);
        }
    }
}
