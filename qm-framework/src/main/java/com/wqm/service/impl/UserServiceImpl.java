package com.wqm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wqm.entity.SysRole;
import com.wqm.entity.SysUser;
import com.wqm.entity.SysUserRole;
import com.wqm.entity.query.AddUserQuery;
import com.wqm.entity.query.ChangeStatusQuery;
import com.wqm.entity.query.UpdateUserQuery;
import com.wqm.entity.vo.PageVo;
import com.wqm.entity.vo.UserDetailVo;
import com.wqm.entity.vo.UserInfoVo;
import com.wqm.entity.vo.UserRoleVo;
import com.wqm.enums.AppHttpCodeEnum;
import com.wqm.exception.SystemException;
import com.wqm.mapper.SysRoleMapper;
import com.wqm.mapper.UserMapper;
import com.wqm.mapper.UserRoleMapper;
import com.wqm.result.ResponseResult;
import com.wqm.service.SysUserService;
import com.wqm.utils.BeanCopyUtils;
import com.wqm.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, SysUser> implements SysUserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private SysRoleMapper roleMapper;
    @Override
    public ResponseResult userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //根据用户id查询用户信息
        SysUser user = getById(userId);
        //封装成UserInfoVo
        UserInfoVo vo = BeanCopyUtils.copyBean(user,UserInfoVo.class);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult updateUserInfo(SysUser user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(SysUser user) {
        if (!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        //对数据进行是否存在的判断
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        save(user);
        return ResponseResult.okResult();


    }

    @Override
    public PageVo getUserList(String userName, String phonenumber, Integer pageNum, Integer pageSize) {

        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<SysUser>()
                                          .eq(SysUser::getDelFlag,0L)
                                          .like(StringUtils.hasText(userName),SysUser::getUserName, userName)
                                          .like(StringUtils.hasText(phonenumber),SysUser::getPhonenumber,phonenumber);
        if (StringUtils.isEmpty(baseMapper.selectList(queryWrapper))){
            return new PageVo();
        }
        Page<SysUser> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        return new PageVo(page.getRecords(),page.getTotal());
    }

    @Override
    public void addUser(AddUserQuery query) {
        //用户名是否为空
        if (!StringUtils.hasText(query.getUserName())){
             new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        //用户名必须未存在
        SysUser existUser = baseMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserName, query.getUserName()));
        if (!StringUtils.isEmpty(existUser)){
            new SystemException(AppHttpCodeEnum.USER_EXIST);
        }
        //手机号必须未存在
        SysUser existPhone = baseMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getPhonenumber, query.getPhonenumber()));
        if (!StringUtils.isEmpty(existPhone)){
            new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }
        //邮箱必须未存在
        SysUser existMail = baseMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getEmail, query.getEmail()));
        if (!StringUtils.isEmpty(existMail)){
            new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        SysUser user = BeanCopyUtils.copyBean(query, SysUser.class);
        //密码加密
        String password = passwordEncoder.encode(query.getPassword());
        user.setPassword(password);
        baseMapper.insert(user);

        for (Long roleId :  query.getRoleIds()){
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(roleId);
            userRoleMapper.insert(userRole);
        }


    }

    @Override
    public void deleteUser(Long id) {
        baseMapper.deleteById(id);
    }

    @Override
    public UserRoleVo getUserById(Long id) {
        SysUser user = baseMapper.selectById(id);
        UserDetailVo userDetailVo = BeanCopyUtils.copyBean(user, UserDetailVo.class);
        List<Long> roleIds = userRoleMapper.selectList(new LambdaQueryWrapper<SysUserRole>()
                        .eq(SysUserRole::getUserId, id)
                        .select(SysUserRole::getRoleId))
                        .stream().map(SysUserRole::getRoleId)
                        .collect(Collectors.toList());
        List<SysRole> roles = new ArrayList<>();
        for (Long roleid : roleIds){
            SysRole role = roleMapper.selectById(roleid);
            roles.add(role);
        }
        UserRoleVo vo = new UserRoleVo();
        vo.setRoles(roles);
        vo.setRoleIds(roleIds);
        vo.setUser(userDetailVo);

        return vo;
    }

    @Override
    public void updateUser(UpdateUserQuery query) {
        Long id = query.getId();
        SysUser user = BeanCopyUtils.copyBean(query, SysUser.class);
        baseMapper.updateById(user);
        userRoleMapper.deleteById(id);
        for (Long roleId:query.getRoleIds()){
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(id);
            userRole.setRoleId(roleId);
            userRoleMapper.insert(userRole);
        }
        

    }

    @Override
    public void changeStatus(ChangeStatusQuery query) {
        SysUser user = baseMapper.selectById(query.getUserId());
        user.setStatus(query.getStatus());
        baseMapper.updateById(user);
    }


    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getNickName,nickName);
        return count(wrapper)>0;
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(SysUser::getUserName,userName);
        return count(queryWrapper)>0;
    }
}
