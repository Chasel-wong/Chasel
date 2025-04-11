package com.wqm.entity.query;

import lombok.Data;

import java.util.List;

@Data
public class UpdateRoleQuery {
    private Long id;
    private String remark;
    private String roleKey;
    private String roleName;
    private String roleSort;
    private String status;
    private List<Long> menuIds;
}
