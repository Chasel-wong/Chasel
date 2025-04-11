package com.wqm.entity.query;

import lombok.Data;

@Data
public class RoleQuery {
    //角色名称
    private String roleName;
    //角色状态（0正常 1停用）
    private String status;
    //删除标志（0代表存在 1代表删除）
    private String delFlag;
}
