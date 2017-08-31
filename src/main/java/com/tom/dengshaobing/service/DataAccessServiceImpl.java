package com.tom.dengshaobing.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.tom.dengshaobing.sqlstatements.SqlStatements;
import com.tom.utils.SqlUtils;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月28日 下午2:34:43
 *
 */

@Service
public class DataAccessServiceImpl implements DataAccessService {

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public <T> T queryForObject(String sqlName, Map<String, Object> paramMap, Class<T> cls) {
		paramMap = SqlUtils.revertKeyUpcase(paramMap);
		return namedParameterJdbcTemplate.queryForObject(SqlStatements.get(sqlName), paramMap, cls);
	}

	@Override
	public Map<String, Object> queryForMap(String sqlName, Map<String, Object> paramMap) {
		paramMap = SqlUtils.revertKeyUpcase(paramMap);
		return namedParameterJdbcTemplate.queryForMap(SqlStatements.get(sqlName), paramMap);
	}

	@Override
	public Map<String, Object> queryForMapAllColumn(String tableName, Map<String, Object> whereParamMap) {
		tableName = tableName.toUpperCase();
		whereParamMap = SqlUtils.revertKeyUpcase(whereParamMap);
		return namedParameterJdbcTemplate.queryForMap(getSelectSqlByTableNameAndParamMap(tableName, whereParamMap),
				whereParamMap);
	}

	@Override
	public int insertSingle(String tableName, Map<String, Object> paramMap) {
		tableName = tableName.toUpperCase();
		paramMap = SqlUtils.revertKeyUpcase(paramMap);
		return namedParameterJdbcTemplate.update(getInsertSqlByTableNameAndParamMap(tableName, paramMap), paramMap);
	}

	@Override
	public int updateSingle(String tableName, Map<String, Object> setParamMap, Map<String, Object> whereParamMap) {
		tableName = tableName.toUpperCase();
		setParamMap = SqlUtils.revertKeyUpcase(setParamMap);
		whereParamMap = SqlUtils.revertKeyUpcase(whereParamMap);

		String sql = getUpdateSqlByTableNameAndParamMap(tableName, setParamMap, whereParamMap);
		setParamMap.putAll(whereParamMap);
		return namedParameterJdbcTemplate.update(sql, setParamMap);
	}

	@Override
	public int deleteSingle(String tableName, Map<String, Object> whereParamMap) {
		tableName = tableName.toUpperCase();
		whereParamMap = SqlUtils.revertKeyUpcase(whereParamMap);

		String sql = getDeleteSqlByTableNameAndParamMap(tableName, whereParamMap);

		return namedParameterJdbcTemplate.update(sql, whereParamMap);
	}

	/**
	 * 根据表名生成全字段insert语句
	 * 
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	private String getInsertSqlByTableNameAndParamMap(String tableName, Map<String, Object> paramMap) {

		StringBuilder returnSql = new StringBuilder();
		StringBuilder paramPlaceholder = new StringBuilder();

		returnSql.append("insert into " + tableName);
		returnSql.append("(");
		paramPlaceholder.append("(");

		for (Entry<String, Object> paramEntry : paramMap.entrySet()) {
			String fieldName = (String) paramEntry.getKey();
			returnSql.append(fieldName + ",");
			paramPlaceholder.append(":" + fieldName + ",");
		}

		// delete last ','
		returnSql.delete(returnSql.length() - 1, returnSql.length());
		paramPlaceholder.delete(paramPlaceholder.length() - 1, paramPlaceholder.length());

		returnSql.append(")");
		paramPlaceholder.append(")");

		returnSql.append(" values ");
		returnSql.append(paramPlaceholder);

		return returnSql.toString().toUpperCase();
	}

	/**
	 * 根据表名生成paramMap中存在的字段update语句
	 * 
	 * @param tableName
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	private String getUpdateSqlByTableNameAndParamMap(String tableName, Map<String, Object> setParamMap,
			Map<String, Object> whereParamMap) {
		StringBuilder returnSql = new StringBuilder();
		StringBuilder whereClauseSql = new StringBuilder();

		returnSql.append("update " + tableName);
		returnSql.append(" set ");

		for (Entry<String, Object> setParamEntry : setParamMap.entrySet()) {
			String fieldName = setParamEntry.getKey();
			returnSql.append(fieldName + "=:" + fieldName + ",");
		}

		for (Entry<String, Object> whereColumn : whereParamMap.entrySet()) {
			whereClauseSql.append(whereColumn.getKey() + "=:" + whereColumn.getKey() + " and ");
		}

		// delete last ','
		returnSql.delete(returnSql.length() - 1, returnSql.length());
		// delete last ' and '
		whereClauseSql.delete(whereClauseSql.length() - 5, whereClauseSql.length());

		returnSql.append(" where ").append(whereClauseSql);
		return returnSql.toString().toUpperCase();
	}

	/**
	 * 根据表名生成全字段select语句
	 * 
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	private String getSelectSqlByTableNameAndParamMap(String tableName, Map<String, Object> whereParamMap) {

		StringBuilder returnSql = new StringBuilder();

		returnSql.append("select * from " + tableName);
		returnSql.append(" where ");

		for (Entry<String, Object> whereColumn : whereParamMap.entrySet()) {
			returnSql.append(whereColumn.getKey() + "=:" + whereColumn.getKey() + " and ");
		}

		// delete last ' and '
		returnSql.delete(returnSql.length() - 5, returnSql.length());
		return returnSql.toString().toUpperCase();
	}

	/**
	 * 根据表名生成paramMap中存在的字段update语句
	 * 
	 * @param tableName
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	private String getDeleteSqlByTableNameAndParamMap(String tableName, Map<String, Object> whereParamMap) {
		StringBuilder returnSql = new StringBuilder();
		StringBuilder whereClauseSql = new StringBuilder();

		returnSql.append("delete from " + tableName);

		for (Entry<String, Object> whereColumn : whereParamMap.entrySet()) {
			whereClauseSql.append(whereColumn.getKey() + "=:" + whereColumn.getKey() + " and ");
		}

		// delete last ' and '
		whereClauseSql.delete(whereClauseSql.length() - 5, whereClauseSql.length());

		returnSql.append(" where ").append(whereClauseSql);
		return returnSql.toString().toUpperCase();
	}

	@Override
	public int update(String sqlName, Map<String, Object> paramMap) {
		paramMap = SqlUtils.revertKeyUpcase(paramMap);
		return namedParameterJdbcTemplate.update(SqlStatements.get(sqlName), paramMap);
	}

	@Override
	public List<Map<String, Object>> queryMapList(String sqlName, Map<String, Object> paramMap) {
		paramMap = SqlUtils.revertKeyUpcase(paramMap);
		return namedParameterJdbcTemplate.queryForList(SqlStatements.get(sqlName), paramMap);
	}

	@Override
	public List<Map<String, Object>> queryMapList(String sqlName) {
		return queryMapList(sqlName, null);
	}

	@Override
	public int upsertSingle(String tableName, Map<String, Object> setParamMap, Set<String> conflictColumns) {
		tableName = tableName.toUpperCase();
		setParamMap = SqlUtils.revertKeyUpcase(setParamMap);
		return namedParameterJdbcTemplate
				.update(getUpsertsertSqlByTableNameAndParamMap(tableName, setParamMap, conflictColumns), setParamMap);

	}

	/**
	 * 根据表名生成paramMap中存在的字段upsert语句
	 * 
	 * @param tableName
	 * @param setParamMap
	 * @param conflictColumns
	 * @return
	 */
	private String getUpsertsertSqlByTableNameAndParamMap(String tableName, Map<String, Object> setParamMap,
			Set<String> conflictColumns) {
		StringBuilder returnSql = new StringBuilder();
		StringBuilder paramPlaceholder = new StringBuilder();

		returnSql.append("insert into " + tableName);
		returnSql.append("(");
		paramPlaceholder.append("(");

		for (Entry<String, Object> paramEntry : setParamMap.entrySet()) {
			String fieldName = paramEntry.getKey();
			returnSql.append(fieldName + ",");
			paramPlaceholder.append(":" + fieldName + ",");
		}

		// delete last ','
		returnSql.delete(returnSql.length() - 1, returnSql.length());
		paramPlaceholder.delete(paramPlaceholder.length() - 1, paramPlaceholder.length());

		returnSql.append(")");
		paramPlaceholder.append(")");

		returnSql.append(" values ");
		returnSql.append(paramPlaceholder);

		returnSql.append(" on conflict (");
		for (String conflict : conflictColumns) {
			returnSql.append(conflict + ",");
		}

		returnSql.delete(returnSql.length() - 1, returnSql.length());

		returnSql.append(") do update set ");

		for (Entry<String, Object> paramEntry : setParamMap.entrySet()) {
			String fieldName = paramEntry.getKey();

			if (conflictColumns.contains(fieldName)) {
				continue;
			}
			returnSql.append(fieldName + "=:" + fieldName + ",");
		}
		returnSql.delete(returnSql.length() - 1, returnSql.length());
		return returnSql.toString().toUpperCase();

	}

}
