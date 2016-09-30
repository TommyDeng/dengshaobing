package com.tom.dengshaobing.service;

import java.util.Map;
import java.util.UUID;

import com.tom.dengshaobing.common.bo.sys.TableMeta;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月28日 下午2:33:40
 *
 */

public interface DataAccessService {

	/**
	 * sql 返回 TableMeta
	 * 
	 * @param sql
	 * @param paramMap
	 * @return
	 */
	TableMeta queryTableMetaBySql(String sql, Map<String, ?> paramMap);

	/**
	 * 执行sql后返回第一行第一列
	 * 
	 * @param sql
	 * @param paramMap
	 * @param cls
	 * @return
	 */
	<T> T queryForOneObject(String sql, Map<String, ?> paramMap, Class<T> cls);

	/**
	 * 单条插入，paramMap中无对应项则赋值为null
	 * 
	 * @param tableName
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	int insertSingle(String tableName, Map<String, ?> paramMap) throws Exception;

	/**
	 * 单条修改，只更新paramMap中对应项,必须包含PK
	 * 
	 * @param tableName
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	int updateSingle(String tableName, Map<String, ?> paramMap) throws Exception;

	
	/**
	 * 根据pk查询单条记录
	 * @param tableName
	 * @param pk
	 * @return
	 * @throws Exception 
	 */
	Map<String, Object> queryRowMapById(String tableName, Object pk) throws Exception;

	/**
	 *  根据pk删除单条记录
	 * @param tableName
	 * @param pk
	 * @return
	 * @throws Exception
	 */
	int deleteRowById(String tableName, Object pk) throws Exception;

}
