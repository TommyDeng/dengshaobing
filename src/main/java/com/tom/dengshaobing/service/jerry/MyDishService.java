package com.tom.dengshaobing.service.jerry;

import java.util.List;
import java.util.Map;

import com.tom.dengshaobing.common.bo.sys.TableMeta;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年11月15日 上午11:08:39
 *
 */

public interface MyDishService {

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

}
