package com.psj.common.constant;


import lombok.Getter;

/**
 * 公告常量类
 *
 * @author Pan.ShiJu
 */
public class GlobalCodeEnum {


    /**
     * 错误码
     */
    public enum ErrorCode {

        /**
         * 错误码
         */
        ERROR_401(401, "Unauthorized"),
        ERROR_500(500, "Internal Server Error"),

        ERROR_1010(1010, "用户名或密码错误！"),
        ERROR_1011(1011, "此帐号已被管理员禁用！"),

        ERROR_1030(1030, "缺少参数错误！"),
        ERROR_1031(1031, "参数错误：id无效！"),

        ERROR_1050(1050, "原密码输入错误！"),
        ERROR_1051(1051, "2次密码输入不一致！"),

        ERROR_1100(1100, "文件不存在！"),
        ERROR_1101(1101, "文件格式不正确！"),
        ERROR_1102(1102, "文件太大！"),

        ERROR_1200(1200, "手机号码重复！"),
        ERROR_1201(1201, "手机号码格式错误！"),

        ERROR_1999(1999, "操作错误！");

        @Getter
        private int key;
        @Getter
        private String value;

        ErrorCode(int key, String value) {
            this.key = key;
            this.value = value;
        }

    }

    /**
     * 成功码
     */
    public enum SuccessCode {

        /**
         *
         */
        SUCCESS(0, "ok"),
        SUCCESS_2001(0, "登录成功！"),
        SUCCESS_2002(0, "退出成功！"),

        SUCCESS_2010(0, "保存成功！"),
        SUCCESS_2020(0, "删除成功！"),

        SUCCESS_2999(0, "操作成功！");

        @Getter
        private int key;
        @Getter
        private String value;

        SuccessCode(int key, String value) {
            this.key = key;
            this.value = value;
        }

    }

}
