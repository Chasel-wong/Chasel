package com.wqm.entity.vo;

import com.wqm.entity.SysRole;
import lombok.Data;

import java.util.List;

@Data
public class UserRoleVo {
   private List<Long> roleIds;
   private UserDetailVo user;
   private List<SysRole> roles;

}
