package com.psj.common.constant.system;

/**
 * <br>
 * <b>功能：</b><br>
 * <b>作者：</b>Pan.ShiJu<br>
 * <b>日期：</b>2017/6/19 16:14<br>
 *
 * @author Administrator
 */
public class RoleEnum {


    /**
     * 角色状态，0：启用，1：禁用
     */
    public enum ROLE_STATUS {

        /**
         * 角色状态启用
         */
        ENABLE(0, "启用"),
        /**
         * 角色状态禁用
         */
        DISABLE(1, "禁用");

        private int key;
        private String value;

        ROLE_STATUS(int key, String value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

    }


}
