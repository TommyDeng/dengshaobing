package com.tom.dengshaobing.service;

import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

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

}
