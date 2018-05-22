package com.psj.common.constant.system;

/**
 * <br>
 * <b>功能：</b><br>
 * <b>作者：</b>Pan.ShiJu<br>
 * <b>日期：</b>2017/6/19 16:14<br>
 *
 * @author Administrator
 */
public class UserEnum {

    /**
     * 帐号类型，0:平台，1：XXX，2：YYY
     */
    public enum ACCOUNT_TYPE {

        /**
         * 账号类型，此处可以修改和项目有关的
         */
        PLATFORM(0, "PLATFORM"),
        VENDOR(1, "XXX"),
        LANDLORD(2, "YYY"),
        OTHER(3, "OTHER");

        private int key;
        private String value;

        ACCOUNT_TYPE(int key, String value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public static String getValueByKey(Integer key) {
            if (key == null) {
                return "OTHER";
            }
            for (ACCOUNT_TYPE e : ACCOUNT_TYPE.values()) {
                if (e.getKey() == key) {
                    return e.getValue();
                }
            }
            return "OTHER";
        }

        public static ACCOUNT_TYPE getAccountType(Integer key) {
            if (key == null) {
                return OTHER;
            }
            for (ACCOUNT_TYPE e : ACCOUNT_TYPE.values()) {
                if (e.getKey() == key) {
                    return e;
                }
            }
            return OTHER;
        }

        public static boolean contains(Integer key) {
            if (key == null) {
                return false;
            }
            for (ACCOUNT_TYPE e : ACCOUNT_TYPE.values()) {
                if (e.getKey() == key) {
                    return true;
                }
            }
            return false;
        }

    }

    /**
     * 帐号状态，0：启用，1：禁用
     */
    public enum ACCOUNT_STATUS {

        /**
         * 启用
         */
        ENABLE(0, "启用"),
        /**
         * 禁用
         */
        DISABLE(1, "禁用");

        private int key;
        private String value;

        ACCOUNT_STATUS(int key, String value) {
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

    /**
     * 帐号状态，0：否，1：是
     */
    public enum SUPER_ADMIN {

        /**
         * 否
         */
        NO(0, "否"),
        /**
         * 是
         */
        YES(1, "是");

        private int key;
        private String value;

        SUPER_ADMIN(int key, String value) {
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
