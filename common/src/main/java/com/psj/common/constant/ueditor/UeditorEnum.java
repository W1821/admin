package com.psj.common.constant.ueditor;

/**
 * @author Administrator
 */
public class UeditorEnum {

    /**
     * action参数
     */
    public enum ACTION {
        /**
         * config
         */
        CONFIG(0, "config"),
        UPLOAD_IMAGE(1, "upload_image"),
        UPLOAD_SCRAWL(2, "upload_scrawl"),
        UPLOAD_VIDEO(3, "upload_video"),
        UPLOAD_FILE(4, "upload_file"),
        CATCH_IMAGE(5, "catch_image"),
        LIST_FILE(6, "list_file"),
        LIST_IMAGE(7, "list_image"),
        ERROR_ACTION(99, "error_action");

        private int key;
        private String value;

        ACTION(int key, String value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public static ACTION getActionByValue(String value) {
            for (ACTION e : ACTION.values()) {
                if (e.getValue().equalsIgnoreCase(value)) {
                    return e;
                }
            }
            return ERROR_ACTION;
        }

    }


}
