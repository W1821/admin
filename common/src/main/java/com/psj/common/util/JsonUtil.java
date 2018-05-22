package com.psj.common.util;

import com.google.gson.Gson;

/**
 * json字符串工具类
 *
 * @author Administrator
 */
public class JsonUtil {

    /**
     * 对象转json字符串
     *
     * @param obj 对象
     * @return json字符串
     */
    public static String objToJsonStr(Object obj) {
        return new Gson().toJson(obj);
    }

}
