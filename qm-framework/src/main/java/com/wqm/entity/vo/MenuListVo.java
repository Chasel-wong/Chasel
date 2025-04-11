package com.wqm.entity.vo;

import com.wqm.entity.SysMenu;
import lombok.Data;

import java.util.List;

@Data
public class MenuListVo {
    //菜单ID
    private Long id;
    private String label;
    //父菜单ID
    private Long parentId;
    private List<MenuListVo> children;
}
