package com.psj.common.util;


import com.psj.pojo.dto.ueditor.State;

/**
 *
 * @author Administrator
 */
public class ResponseUtil {

    /**
     * 返回错误信息
     *
     * @param errorMsg 错误信息字符串
     */
    public static String error(String errorMsg) {
        return JsonUtil.objToJsonString(new State(errorMsg));
    }

    /**
     * 成功返回
     */
    public static String success(Object obj) {
        return JsonUtil.objToJsonString(obj);
    }


}
