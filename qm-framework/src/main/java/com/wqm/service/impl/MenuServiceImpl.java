package com.wqm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wqm.entity.SysMenu;
import com.wqm.entity.SysRoleMenu;
import com.wqm.entity.SystemConstants;
import com.wqm.entity.query.MenuQuery;
import com.wqm.entity.request.MenuReq;
import com.wqm.entity.vo.MenuListByIdVo;
import com.wqm.entity.vo.MenuListVo;
import com.wqm.enums.AppHttpCodeEnum;
import com.wqm.exception.SystemException;
import com.wqm.mapper.RoleMenuMapper;
import com.wqm.mapper.SysMenuMapper;
import com.wqm.result.ResponseResult;
import com.wqm.service.MenuService;
import com.wqm.utils.BeanCopyUtils;
import com.wqm.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements MenuService {
    @Autowired
    private SysMenuMapper menuMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Override
    public List<String> selectPermsByUserId(Long id) {
        if (id == 1L){
            LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(SysMenu::getMenuType, SystemConstants.Menu,SystemConstants.Button);
            queryWrapper.eq(SysMenu::getStatus,SystemConstants.LINK_STATUS_NORMAL);
            List<SysMenu> list = list(queryWrapper);
            List<String> perms = list.stream().map(SysMenu::getPerms).collect(Collectors.toList());
            return perms;
        }
        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<SysMenu> selectRouterMenuTreeByUserId(Long userId) {
        SysMenuMapper menuMapper = getBaseMapper();
        List<SysMenu> menus = null;
        //判断是否是管理员
        if(SecurityUtils.isAdmin()){
            //如果是 获取所有符合要求的Menu
            menus = menuMapper.selectAllRouterMenu();
        }else{
            //否则  获取当前用户所具有的Menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }

        //构建tree
        //先找出第一层的菜单  然后去找他们的子菜单设置到children属性中
        List<SysMenu> menuTree = builderMenuTree(menus,0L);
        return menuTree;
    }
    private List<SysMenu> builderMenuTree(List<SysMenu> menus, Long parentId) {
        List<SysMenu> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuTree;
    }
    private List<SysMenu> getChildren(SysMenu menu, List<SysMenu> menus) {
        List<SysMenu> childrenList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m->m.setChildren(getChildren(m,menus)))
                .collect(Collectors.toList());
        return childrenList;
    }
    @Override
    public List<MenuListVo> getTreeLSelect() {
        List<MenuListVo> menuListVos = baseMapper.selectMenu();
        List<MenuListVo> list = buildTree(menuListVos, 0L);
        return list;
    }

    @Override
    public MenuListByIdVo getTreeLSelectById(Long id) {
        List<SysRoleMenu> roleMenus = roleMenuMapper.selectList(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, id));
        List<Long> menuIds = roleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
        List<MenuListVo> treeLSelect = getTreeLSelect();
        MenuListByIdVo result = new MenuListByIdVo();
        result.setMenus(treeLSelect);
        result.setCheckedKeys(menuIds);
        return result;

    }

    public List<MenuListVo> buildTree(List<MenuListVo> menus,Long parentId){
        List<MenuListVo> menuListVos = menus.stream()
                                     .filter(menu -> menu.getParentId().equals(parentId))
                                     .collect(Collectors.toList());
        for (MenuListVo vo:menuListVos){
            vo.setChildren(buildTree(menus,vo.getId()));
        }
        return menuListVos;

    }

    @Override
    public List<MenuReq> getMenuList(String status,String menuName) {
        List<MenuReq> menuReqs = menuMapper.selectMenuListVo(status,menuName);
        Set<Long> menuIdList = menuReqs.stream().map(MenuReq::getId).collect(Collectors.toSet());
        List<MenuReq> resultList  = new ArrayList<>();
        for (MenuReq menuVo:menuReqs){
            Long parentId = menuVo.getParentId();
            if (!menuIdList.contains(parentId)){
                menuIdList.add(parentId);
                resultList.addAll(recurMenuList(parentId,menuReqs));
            }
          resultList.add(menuVo);
        }
        return resultList;

    }

    @Override
    public void addMenu(MenuReq menuReq) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMenu::getMenuName,menuReq.getMenuName());
        SysMenu result = menuMapper.selectOne(queryWrapper);
        if (result!=null){
         throw  new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
     }
        SysMenu sysMenu = BeanCopyUtils.copyBean(menuReq, SysMenu.class);
        baseMapper.insert(sysMenu);

    }

    @Override
    public MenuQuery menuQuery(Long id) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        SysMenu result = menuMapper.selectOne(queryWrapper.eq(SysMenu::getId, id));
        if (result==null){
            throw new SystemException(AppHttpCodeEnum.MENU_NOT_EXIST);
        }
        MenuQuery menuQuery = BeanCopyUtils.copyBean(result, MenuQuery.class);
        return menuQuery;
    }

    @Override
    public void updateMenu(MenuQuery menuQuery) {
        SysMenu menu = BeanCopyUtils.copyBean(menuQuery, SysMenu.class);
        baseMapper.updateById(menu);

    }

    @Override
    public void deleteMenu(Long menuId) {
        baseMapper.deleteById(menuId);

    }




    public List<MenuReq> recurMenuList(Long parentId,List<MenuReq> menuList){
        return menuList.stream().filter(menuVo-> menuVo.getParentId().equals(parentId))
                                .peek(menuVo->menuVo.setChildren(recurMenuList(menuVo.getId(),menuList)))
                                .collect(Collectors.toList());

    }




    /**
     * 获取存入参数的 子Menu集合
     * @param menu
     * @param menus
     * @return
     */

}
