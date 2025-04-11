package com.wqm.entity.query;

import lombok.Data;

import java.util.List;

@Data
public class UpdateUserQuery {
    private Long id;

    private String nickName;

    private String userName;

    private String sex;

    private String email;

    private String status;

    private List<Long> roleIds;
}
