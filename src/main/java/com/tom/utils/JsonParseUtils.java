package com.tom.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
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
		if (obj == null)
			return null;

		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		return gson.toJson(obj);

	}

	public static <T> T generateJavaBean(String jsonStr, Class<T> cls) throws Exception {
		// Deserialization
		if (StringUtils.isBlank(jsonStr))
			return null;

		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		return gson.fromJson(jsonStr, cls);
	}

	public static String getStringValueByFieldName(String jsonStr, String fieldName) throws Exception {
		if (StringUtils.isBlank(jsonStr) || StringUtils.isBlank(fieldName))
			return null;

		JsonObject jsonObject = new JsonParser().parse(jsonStr).getAsJsonObject();
		return jsonObject.get(fieldName).getAsString();
	}

	public static List<String> getListValueByFieldName(String jsonStr, String fieldName) throws Exception {
		if (StringUtils.isBlank(jsonStr) || StringUtils.isBlank(fieldName))
			return null;

		JsonObject jsonObject = new JsonParser().parse(jsonStr).getAsJsonObject();
		List<String> list = new ArrayList<String>();
		for (JsonElement element : jsonObject.get(fieldName).getAsJsonArray()) {
			list.add(element.getAsString());
		}
		return list;
	}
}
