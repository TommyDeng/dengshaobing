package com.tom.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年10月5日 下午4:54:53
 *
 */

public class SqlUtils {
	public static Map<String, Object> revertKeyUpcase(Map<String, Object> map) {
		if (map == null || map.size() == 0) {
			return map;
		}

		Map<String, Object> returnMap = new HashMap<>();

		for (String key : map.keySet()) {
			Object value = map.get(key);
			returnMap.put(key.toUpperCase(), value);
		}
		return returnMap;
	}
}
