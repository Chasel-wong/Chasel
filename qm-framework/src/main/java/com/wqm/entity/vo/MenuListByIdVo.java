package com.wqm.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class MenuListByIdVo {
    private List<MenuListVo> menus;
    private List<Long> checkedKeys;
}
