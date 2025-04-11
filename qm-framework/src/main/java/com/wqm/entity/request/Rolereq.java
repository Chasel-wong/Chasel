package com.wqm.entity.request;

import lombok.Data;

import java.util.List;

@Data
public class Rolereq {
    private String roleName;
    private String roleKey;
    //显示顺序
    private Integer roleSort;
    //角色状态（0正常 1停用）
    private String status;
    private List<Long> menuId;
    private String remark;

}
