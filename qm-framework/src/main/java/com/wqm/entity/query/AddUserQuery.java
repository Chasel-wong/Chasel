package com.wqm.entity.query;

import lombok.Data;

import java.util.List;

@Data
public class AddUserQuery {
    private String userName;
    private String nickName;
    private String password;
    private String phonenumber;
    private String email;
    private String sex;
    private String status;
    private List<Long> roleIds;
}

