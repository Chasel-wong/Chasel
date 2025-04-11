package com.wqm.entity.vo;

import lombok.Data;

@Data
public class UserDetailVo {
    private Long id;

    private String nickName;

    private String userName;

    private String sex;

    private String email;

    private String status;
}
