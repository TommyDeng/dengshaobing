package com.tom.dengshaobing.service;

import java.util.List;
import java.util.Map;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月14日 上午11:30:12
 *
 */

public interface CommonService {

	/**
	 * 记录用户登陆日志
	 * 
	 * @param visitorName
	 */
	void logVisit(String name);

	/**
	 * 查询所有用户
	 * @return 
	 */
	List<Map<String, Object>> listVisit();

}
