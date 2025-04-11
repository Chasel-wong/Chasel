package com.wqm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wqm.entity.SysRole;
import com.wqm.entity.query.RoleQuery;
import com.wqm.entity.vo.RoleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 角色信息表(SysRole)表数据库访问层
 *
 * @author makejava
 * @since 2025-01-18 21:59:17
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<String> selectRoleKeyByUserId(Long id);
    Long selectBackRoleCount(@Param("roleName")String roleName,@Param("status")String status);
    List<RoleVo> selectBackRoleList(@Param("param")RoleQuery roleQuery);
}

