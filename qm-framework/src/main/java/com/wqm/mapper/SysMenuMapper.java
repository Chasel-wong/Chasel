package com.wqm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wqm.entity.SysMenu;
import com.wqm.entity.query.MenuQuery;
import com.wqm.entity.request.MenuReq;
import com.wqm.entity.vo.MenuListVo;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


/**
 * 菜单权限表(SysMenu)表数据库访问层
 *
 * @author makejava
 * @since 2025-01-18 11:14:44
 */

public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<String> selectPermsByUserId(Long userId);

    List<SysMenu> selectAllRouterMenu();

    List<SysMenu> selectRouterMenuTreeByUserId(Long userId);

    List<MenuReq> selectMenuListVo(@Param("status") String status,@Param("menuName") String menuName);

    List<MenuListVo> selectMenu();

    MenuListVo selectMenuById(@Param("menuId") Long menuId);
}

