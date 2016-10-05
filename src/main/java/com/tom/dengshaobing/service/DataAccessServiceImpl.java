package com.tom.dengshaobing.service;

import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;
import org.springframework.jdbc.core.namedparam.ParsedSql;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedCaseInsensitiveMap;

import com.tom.dengshaobing.common.bo.sys.HeadMeta;
import com.tom.dengshaobing.common.bo.sys.TableMeta;
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
	public TableMeta queryTableMeta(String sqlName, Map<String, Object> paramMap) {
		paramMap = SqlUtils.revertKeyUpcase(paramMap);
		TableMeta tableMeta = new TableMeta();
		namedParameterJdbcTemplate.query(SqlStatements.get(sqlName), paramMap,
				new ResultSetExtractor<List<Map<String, Object>>>() {
					@Override
					public List<Map<String, Object>> extractData(ResultSet resultSet)
							throws SQLException, DataAccessException {

						List<Map<String, Object>> dataList = new ArrayList<>();
						List<HeadMeta> headList = new ArrayList<>();

						// set meta data
						ResultSetMetaData rsmd = resultSet.getMetaData();
						int columnCount = rsmd.getColumnCount();

						for (int i = 1; i <= columnCount; i++) {
							HeadMeta headMeta = new HeadMeta();
							headMeta.index = i;
							headMeta.columnLabel = rsmd.getColumnLabel(i).toUpperCase();
							headMeta.columnName = String.valueOf(i);// use index
							headMeta.className = rsmd.getColumnClassName(i);
							headMeta.display = !"UNIQUE_CODE".equals(headMeta.columnLabel);

							headList.add(headMeta);
						}

						while (resultSet.next()) {
							Map<String, Object> mapOfColValues = new LinkedCaseInsensitiveMap<Object>(columnCount);
							for (int i = 1; i <= columnCount; i++) {
								// String key = lookupColumnName(rsmd, i);
								Object obj = getResultSetValue(resultSet, i);
								mapOfColValues.put(String.valueOf(i), obj);// use
																			// index
							}
							dataList.add(mapOfColValues);
						}
						tableMeta.dataList = dataList;
						tableMeta.headList = headList;
						return dataList;
					}
				});

		return tableMeta;
	}

	// COPY from org.springframework.jdbc.support.JdbcUtils
	// modified ColumnLabel and ColumnName order
	@SuppressWarnings("unused")
	private static String lookupColumnName(ResultSetMetaData resultSetMetaData, int columnIndex) throws SQLException {
		String name = resultSetMetaData.getColumnName(columnIndex);
		if (name == null || name.length() < 1) {
			name = resultSetMetaData.getColumnLabel(columnIndex);
		}
		return name;
	}

	// COPY from org.springframework.jdbc.support.JdbcUtils
	private static Object getResultSetValue(ResultSet rs, int index) throws SQLException {
		Object obj = rs.getObject(index);
		String className = null;
		if (obj != null) {
			className = obj.getClass().getName();
		}
		if (obj instanceof Blob) {
			Blob blob = (Blob) obj;
			obj = blob.getBytes(1, (int) blob.length());
		} else if (obj instanceof Clob) {
			Clob clob = (Clob) obj;
			obj = clob.getSubString(1, (int) clob.length());
		} else if ("oracle.sql.TIMESTAMP".equals(className) || "oracle.sql.TIMESTAMPTZ".equals(className)) {
			obj = rs.getTimestamp(index);
		} else if (className != null && className.startsWith("oracle.sql.DATE")) {
			String metaDataClassName = rs.getMetaData().getColumnClassName(index);
			if ("java.sql.Timestamp".equals(metaDataClassName) || "oracle.sql.TIMESTAMP".equals(metaDataClassName)) {
				obj = rs.getTimestamp(index);
			} else {
				obj = rs.getDate(index);
			}
		} else if (obj != null && obj instanceof java.sql.Date) {
			if ("java.sql.Timestamp".equals(rs.getMetaData().getColumnClassName(index))) {
				obj = rs.getTimestamp(index);
			}
		}
		return obj;
	}

	@Override
	public <T> T queryForOneObject(String sqlName, Map<String, Object> paramMap, Class<T> cls) {

		paramMap = SqlUtils.revertKeyUpcase(paramMap);

		List<T> resultList = namedParameterJdbcTemplate.query(SqlStatements.get(sqlName), paramMap, new RowMapper<T>() {

			@Override
			@SuppressWarnings("unchecked")
			public T mapRow(ResultSet rs, int rowNum) throws SQLException {
				// 取第一列
				// H2 [Not supported] without throw unsupported,fuck!
				// return rs.getObject(1, cls);
				return (T) rs.getObject(1);
			}

		});
		if (resultList == null || resultList.isEmpty()) {
			return null;
		} else {
			return resultList.get(0);// 取第一行
		}
	}

	@Override
	public int insertSingle(String tableName, Map<String, Object> paramMap) throws Exception {
		tableName = tableName.toUpperCase();
		paramMap = SqlUtils.revertKeyUpcase(paramMap);
		String insertSql = getInsertSqlByTableNameAndParamMap(tableName, paramMap).toUpperCase();
		setNullIfPlaceHolderNotExistsInParamMap(insertSql, paramMap);
		return namedParameterJdbcTemplate.update(insertSql, paramMap);
	}

	@Override
	public int updateSingle(String tableName, Map<String, Object> paramMap) throws Exception {
		tableName = tableName.toUpperCase();
		paramMap = SqlUtils.revertKeyUpcase(paramMap);
		return namedParameterJdbcTemplate.update(getUpdateSqlByTableNameAndParamMap(tableName, paramMap), paramMap);
	}

	/**
	 * 根据表名生成全字段insert语句
	 * 
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	protected String getInsertSqlByTableNameAndParamMap(String tableName, Map<String, Object> paramMap)
			throws Exception {
		List<Map<String, Object>> columnsDescList = getColumnsDescListByTableName(tableName);

		StringBuilder returnSql = new StringBuilder();
		StringBuilder paramPlaceholderSql = new StringBuilder();

		returnSql.append("insert into " + tableName);
		returnSql.append("(");
		paramPlaceholderSql.append("(");

		for (Map<String, Object> columnDesc : columnsDescList) {
			// columnDesc.get("FIELD");// COLUMN_NAME
			// columnDesc.get("TYPE");// VARCHAR(2500)
			// columnDesc.get("NULL");// YES or NO
			// columnDesc.get("KEY");// PRI or ''
			// columnDesc.get("DEFAULT");// NULL

			String fieldName = (String) columnDesc.get("FIELD");
			// 有默认值,paramMap无设置,则不做处理
			if (!"NULL".equals(columnDesc.get("DEFAULT")) && !paramMap.containsKey(fieldName)) {
				continue;
			}
			returnSql.append(fieldName);
			returnSql.append(",");

			paramPlaceholderSql.append(":" + fieldName);
			paramPlaceholderSql.append(",");
		}
		returnSql.delete(returnSql.length() - 1, returnSql.length());
		paramPlaceholderSql.delete(paramPlaceholderSql.length() - 1, paramPlaceholderSql.length());

		returnSql.append(")");
		paramPlaceholderSql.append(")");

		returnSql.append(" values ");
		returnSql.append(paramPlaceholderSql);

		return returnSql.toString();
	}

	/**
	 * 根据表名生成paramMap中存在的字段update语句
	 * 
	 * @param tableName
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	protected String getUpdateSqlByTableNameAndParamMap(String tableName, Map<String, Object> paramMap)
			throws Exception {
		List<Map<String, Object>> columnsDescList = getColumnsDescListByTableName(tableName);

		StringBuilder returnSql = new StringBuilder();

		StringBuilder whereClauseSql = new StringBuilder();

		boolean updateFeildEmpty = true;
		returnSql.append("update " + tableName);
		returnSql.append(" set ");

		for (Map<String, Object> columnDesc : columnsDescList) {
			// columnDesc.get("FIELD");// COLUMN_NAME
			// columnDesc.get("TYPE");// VARCHAR(2500)
			// columnDesc.get("NULL");// YES or NO
			// columnDesc.get("KEY");// PRI or ''
			// columnDesc.get("DEFAULT");// NULL

			String fieldName = (String) columnDesc.get("FIELD");

			// PK for where clause
			if ("PRI".equals(columnDesc.get("KEY"))) {
				if (paramMap.containsKey(fieldName)) {
					// only support for 1 pk
					whereClauseSql.append(fieldName + "=:" + fieldName);
				} else {// PK not exists in paramMap
					throw new Exception("PK not exists in paramMap" + fieldName);
				}
			} else {
				// paramMap contains then set field value
				if (paramMap.containsKey(fieldName)) {
					returnSql.append(fieldName + "=:" + fieldName);
					returnSql.append(",");
					updateFeildEmpty = false;
				}
			}
		}

		if (updateFeildEmpty) {
			throw new Exception("update feild empty in paramMap");
		}
		// delete ,
		returnSql.delete(returnSql.length() - 1, returnSql.length());
		returnSql.append(" where ").append(whereClauseSql);
		return returnSql.toString();
	}

	/**
	 * 获取表字段说明
	 * 
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	protected List<Map<String, Object>> getColumnsDescListByTableName(String tableName) throws Exception {
		String descTableSql = "show columns from " + tableName;
		Map<String, Object> paramMap = new HashMap<>();
		// paramMap.put("TABLE_NAME", tableName);

		List<Map<String, Object>> columnsDescList = namedParameterJdbcTemplate.queryForList(descTableSql, paramMap);
		if (columnsDescList == null || columnsDescList.size() == 0) {
			throw new Exception("table not exsits :" + tableName);
		}
		return columnsDescList;
	}

	/**
	 * 将paramMap中不存在的于sql中的place holder赋值为null
	 * 
	 * @param sql
	 * @param paramMap
	 * @throws Exception
	 */
	private static void setNullIfPlaceHolderNotExistsInParamMap(String sql, Map<String, Object> paramMap)
			throws Exception {
		ParsedSql parsedSql = NamedParameterUtils.parseSqlStatement(sql);
		// ParsedSql in org.springframework.jdbc.core.namedparam
		// List<String> paramNames = parsedSql.getParameterNames();

		// Method[] methods = ParsedSql.class.getMethods();
		// Method[] methodsUnvisiable = ParsedSql.class.getDeclaredMethods();
		// method not visible, use reflection to access!
		Method method = ParsedSql.class.getDeclaredMethod("getParameterNames");
		method.setAccessible(true);

		@SuppressWarnings("unchecked")
		List<String> paramNames = (List<String>) method.invoke(parsedSql);

		for (String paramName : paramNames) {
			if (!paramMap.containsKey(paramName)) {
				paramMap.put(paramName, null);
			}
		}
	}

	@Override
	public Map<String, Object> queryRowMapById(String tableName, Object pk) throws Exception {
		tableName = tableName.toUpperCase();
		String sql = "select * from " + tableName + " where ";
		List<Map<String, Object>> columnsDescList = getColumnsDescListByTableName(tableName);

		String pkFieldName = "";
		for (Map<String, Object> columnsDesc : columnsDescList) {
			if ("PRI".equals((String) columnsDesc.get("KEY"))) {
				pkFieldName = (String) columnsDesc.get("FIELD");
				break;
			}
		}

		sql += pkFieldName + " = :" + pkFieldName;
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(pkFieldName, pk);

		List<Map<String, Object>> mapList = namedParameterJdbcTemplate.queryForList(sql, paramMap);
		if (mapList == null || mapList.size() == 0) {
			return null;
		} else {
			return mapList.get(0);
		}

	}

	@Override
	public int deleteRowById(String tableName, Object pk) throws Exception {
		tableName = tableName.toUpperCase();
		String sql = "delete from " + tableName + " where ";
		List<Map<String, Object>> columnsDescList = getColumnsDescListByTableName(tableName);

		String pkFieldName = "";
		for (Map<String, Object> columnsDesc : columnsDescList) {
			if ("PRI".equals((String) columnsDesc.get("KEY"))) {
				pkFieldName = (String) columnsDesc.get("FIELD");
				break;
			}
		}

		sql += pkFieldName + " = :" + pkFieldName;
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(pkFieldName, pk);
		return namedParameterJdbcTemplate.update(sql, paramMap);
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

}
