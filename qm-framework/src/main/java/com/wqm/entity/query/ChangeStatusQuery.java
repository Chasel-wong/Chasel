package com.wqm.entity.query;

import lombok.Data;

@Data
public class ChangeStatusQuery {
    private Long userId;
    private  String status;
}
