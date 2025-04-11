package com.wqm.entity.vo;

import lombok.Data;

@Data
public class RoleByIdVo {
    //角色ID
    private Long id;
    //角色名称
    private String roleName;
    //角色权限字符串
    private String roleKey;
    //显示顺序
    private Integer roleSort;
    //角色状态（0正常 1停用）
    private String status;

    private String remark;
}
