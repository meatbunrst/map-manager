//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cn.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * @author dgzhangheng
 */
public class JsonHelper {
    public JsonHelper() {
    }

    public static String toJson(Object o) {
        return JSON.toJSONString(o);
    }

    public static Object toObject(String json) {
        return JSON.parse(json);
    }

    public static JSONObject toJsonObject(String json) {
        return JSON.parseObject(json);
    }

    public static JSONArray toJsonArrayObject(String json) {
        return JSON.parseArray(json);
    }

    public static Map<String, Object> jsonToMap(Object o) {
        return jsonToMap(toJson(o));
    }

    public static Map<String, Object> jsonToMap(String json) {
        return (Map)toObject(json);
    }

    public static <T> T jsonToEntity(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    public static <T> List<T> jsonToEntityList(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }
}
