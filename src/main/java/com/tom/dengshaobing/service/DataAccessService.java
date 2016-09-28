package com.tom.dengshaobing.service;

import java.util.Map;

import com.tom.dengshaobing.common.bo.sys.TableMeta;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月28日 下午2:33:40
 *
 */

public interface DataAccessService {

	/**
	 * @param sql
	 * @param paramMap
	 * @return
	 */
	TableMeta queryTableMetaBySql(String sql, Map<String, ?> paramMap);

}
