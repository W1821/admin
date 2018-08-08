package com.psj.common.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
    public static String objToJsonString(Object obj) {
        return new Gson().toJson(obj);
    }

    /**
     * json字符串转对象
     */
    public static <T> T jsonStringToObject(String jsonStr, Class<T> classOfT) {
        return new Gson().fromJson(jsonStr, classOfT);
    }

    /**
     * 对象转json字符串
     */
    public static JsonObject stringToJsonObject(String jsonStr) {
        return new JsonParser().parse(jsonStr).getAsJsonObject();
    }


    /**
     * 返回String类型
     */
    public static JsonObject getJsonObject(String jsonStr, String key) {
        return stringToJsonObject(jsonStr).getAsJsonObject(key);
    }

    /**
     * 返回JsonObject类型
     */
    public static JsonObject getJsonString(String jsonStr, String key) {
        return stringToJsonObject(jsonStr).getAsJsonObject(key);
    }

    /**
     * 返回int类型
     */
    public static int getJsonInt(String jsonStr, String key) {
        return stringToJsonObject(jsonStr).getAsJsonObject(key).getAsInt();
    }

    /**
     * 移除json中某个键值对
     */
    public static String remove(String jsonStr, String key) {
        JsonObject jsonObject = stringToJsonObject(jsonStr);
        jsonObject.remove(key);
        return jsonObject.toString();
    }


}
