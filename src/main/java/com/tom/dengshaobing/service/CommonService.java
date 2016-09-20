package com.tom.dengshaobing.service;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.apache.http.client.fluent.Form;

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
	 * 
	 * @return
	 */
	List<Map<String, Object>> listVisit();

	/**
	 * 调用httpGet，并记录调用日志
	 * @param uri
	 * @return
	 * @throws Exception
	 */
	String httpGet(URI uri) throws Exception;

	/**
	 * 调用httpPost，并记录调用日志
	 * @param uri
	 * @param form
	 * @return
	 * @throws Exception
	 */
	String httpPost(URI uri,Form form ) throws Exception;
}
