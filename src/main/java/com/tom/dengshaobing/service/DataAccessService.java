package com.tom.dengshaobing.service;

import java.util.List;
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
	 * @param sqlName
	 * @param paramMap
	 * @return
	 */
	TableMeta queryTableMeta(String sqlName, Map<String, Object> paramMap);

	/**
	 * 执行sql后返回第一行第一列
	 * 
	 * @param sql
	 * @param paramMap
	 * @param cls
	 * @return
	 */
	<T> T queryForOneObject(String sqlName, Map<String, Object> paramMap, Class<T> cls);

	/**
	 * 执行sql后返回第一行Map
	 * 
	 * @param sql
	 * @param paramMap
	 * @return
	 */
	Map<String, Object> queryForOneRowMap(String sqlName, Map<String, Object> paramMap);

	
	/**
	 * 根据pk查询单条记录,返回所有字段
	 * 
	 * @param tableName
	 * @param pk
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> queryRowMapById(String tableName, Object pk) throws Exception;

	
	/**
	 * 单条插入，paramMap中无对应项则赋值为null
	 * 
	 * @param tableName
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	int insertSingle(String tableName, Map<String, Object> paramMap) throws Exception;

	/**
	 * 单条修改，只更新paramMap中对应项,必须包含PK
	 * 
	 * @param tableName
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	int updateSingle(String tableName, Map<String, Object> paramMap) throws Exception;

	/**
	 * 有记录是修改，无记录时插入，只更新paramMap中对应项,必须包含PK
	 * 
	 * @param tableName
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	int mergeSingle(String tableName, Map<String, Object> paramMap) throws Exception;

	/**
	 * 根据pk删除单条记录
	 * 
	 * @param tableName
	 * @param pk
	 * @return
	 * @throws Exception
	 */
	int deleteRowById(String tableName, Object pk) throws Exception;

	/**
	 * 执行sql
	 * 
	 * @param sqlName
	 * @param paramMap
	 * @return
	 */
	int update(String sqlName, Map<String, Object> paramMap);

	/**
	 * 查询MapList
	 * @param sqlName
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryMapList(String sqlName, Map<String, Object> paramMap);
	
	/**
	 * 查询MapList
	 * @param sqlName
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> queryMapList(String sqlName);
}
