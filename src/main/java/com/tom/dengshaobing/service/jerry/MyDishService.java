package com.tom.dengshaobing.service.jerry;

import java.util.Map;
import java.util.UUID;

import com.tom.dengshaobing.common.bo.sys.TableMeta;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年11月15日 上午11:08:39
 *
 */

public interface MyDishService {

	TableMeta listAllCookbook();

	/**
	 * @param recommendCount
	 * @return
	 */
	TableMeta getRecommendedMeatList(int recommendCount);

	/**
	 * @param recommendCount
	 * @return
	 */
	TableMeta getRecommendedVegeList(int recommendCount);

	Map<String, Object> queryCookbook(UUID fromString, String AT) throws Exception;

	void addCookbook(Map<String, Object> properties, String AT) throws Exception;

	void deleteCookbook(UUID fromString, String AT) throws Exception;

	void updateCookbook(Map<String, Object> properties, String AT) throws Exception;

	void listAllCookbook(String AT);

}
