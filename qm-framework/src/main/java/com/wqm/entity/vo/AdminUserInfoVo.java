package com.wqm.entity.vo;

import com.wqm.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class AdminUserInfoVo {
    private List<String> permissions;
    private List<String> role;
    private UserInfoVo user;
}
