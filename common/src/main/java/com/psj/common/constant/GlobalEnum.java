package com.psj.common.constant;

/**
 * <br>
 * <b>功能：</b>公共枚举类<br>
 * <b>作者：</b>Pan.ShiJu<br>
 * <b>日期：</b>2017/4/11 23:27<br>
 * @author Administrator
 */
public class GlobalEnum {

    /**
     * 删除状态
     */
    public enum DELETED {
        /**
         * 未删除
         */
        NO(0, "N"),
        /**
         * 已删除
         */
        YES(1, "Y");

        private int key;
        private String value;

        DELETED(int key, String value) {
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
     * 图片格式
     */
    public enum IMAGE_FIELDS {

        /**
         * png图片
         */
        PNG(".png"),
        JPEG(".jpg"),
        JPG(".jpeg"),
        GIF(".gif");

        private String value;

        IMAGE_FIELDS(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static boolean containsIgnoreCase(String imageField) {
            if (imageField == null) {
                return false;
            }
            for (IMAGE_FIELDS e : IMAGE_FIELDS.values()) {
                if (imageField.equalsIgnoreCase(e.getValue())) {
                    return true;
                }
            }
            return false;
        }

    }

}
