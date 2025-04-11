package com.wqm.entity.request;

import lombok.Data;

@Data
public class ChangeStatusReq {
    private Long roleId;
    private String status;
}
