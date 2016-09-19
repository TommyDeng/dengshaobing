package com.tom.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月19日 下午12:08:16
 *
 */

public class JsonParseUtils {

	public static String generateJsonString(Object obj) throws Exception {
		// Serialization
		Gson gson = new Gson();
		return gson.toJson(obj);

	}

	public static <T> T generateJavaBean(String jsonStr, Class<T> cls) throws Exception {
		// Deserialization
		Gson gson = new Gson();
		return gson.fromJson(jsonStr, cls);
	}

	public static String getValueByFieldName(String jsonStr, String fieldName) throws Exception {
		JsonObject jsonObject = new JsonParser().parse(jsonStr).getAsJsonObject();
		return jsonObject.get(fieldName).getAsString();
	}

}
