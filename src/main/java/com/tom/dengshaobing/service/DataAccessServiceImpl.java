package com.tom.dengshaobing.service;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedCaseInsensitiveMap;

import com.tom.dengshaobing.common.bo.sys.HeadMeta;
import com.tom.dengshaobing.common.bo.sys.TableMeta;

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
	public TableMeta queryTableMetaBySql(String sql, Map<String, ?> paramMap) {
		TableMeta tableMeta = new TableMeta();
		namedParameterJdbcTemplate.query(sql, paramMap, new ResultSetExtractor<List<Map<String, Object>>>() {
			@Override
			public List<Map<String, Object>> extractData(ResultSet resultSet) throws SQLException, DataAccessException {

				List<Map<String, Object>> dataList = new ArrayList<>();
				List<HeadMeta> headList = new ArrayList<>();

				// set meta data
				ResultSetMetaData rsmd = resultSet.getMetaData();
				int columnCount = rsmd.getColumnCount();

				for (int i = 1; i <= columnCount; i++) {
					HeadMeta headMeta = new HeadMeta();
					headMeta.index = i;
					headMeta.columnLabel = rsmd.getColumnLabel(i);
					headMeta.columnName = String.valueOf(i);// use index
					headMeta.className = rsmd.getColumnClassName(i);

					headList.add(headMeta);
				}

				while (resultSet.next()) {
					Map<String, Object> mapOfColValues = new LinkedCaseInsensitiveMap<Object>(columnCount);
					for (int i = 1; i <= columnCount; i++) {
						// String key = lookupColumnName(rsmd, i);
						Object obj = getResultSetValue(resultSet, i);
						mapOfColValues.put(String.valueOf(i), obj);// use index
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
}
