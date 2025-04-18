package com.wqm.enums;

public enum AppHttpCodeEnum {

    // 成功
    SUCCESS(200,"操作成功"),

    // 登录
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(501,"用户名已存在"),
    PHONENUMBER_EXIST(502,"手机号已存在"),
    EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    LOGIN_ERROR(505,"用户名或密码错误"),
    CONTENT_NOT_NULL(506, "内容不能为空"),
    USERNAME_NOT_NULL(601,"用户名不能为空"),
    PASSWORD_NOT_NULL(602, "密码不能为空"),
    EMAIL_NOT_NULL(603, "电子邮箱不能为空"),
    NICKNAME_NOT_NULL(604, "昵称不能为空"),
    MENU_NOT_EXIST(605, "菜单不存在"),
    NICKNAME_EXIST(606, "昵称已存在"),
    USER_EXIST(607, "用户已存在");


    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
