package com.wqm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wqm.entity.SysMenu;
import com.wqm.entity.query.MenuQuery;
import com.wqm.entity.request.MenuReq;
import com.wqm.entity.vo.MenuListByIdVo;
import com.wqm.entity.vo.MenuListVo;
import com.wqm.result.ResponseResult;

import java.util.List;

public interface MenuService extends IService<SysMenu> {
    List<String> selectPermsByUserId(Long id);

    List<SysMenu> selectRouterMenuTreeByUserId(Long userId);

    List<MenuReq> getMenuList(String status,String menuName);

    void addMenu(MenuReq menuReq);


    MenuQuery menuQuery(Long id);


    void updateMenu(MenuQuery menuQuery);

    void deleteMenu(Long menuId);

    List<MenuListVo> getTreeLSelect();

    MenuListByIdVo getTreeLSelectById(Long id);
}
