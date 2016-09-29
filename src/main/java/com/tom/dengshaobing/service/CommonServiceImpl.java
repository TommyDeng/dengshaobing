package com.tom.dengshaobing.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tom.dengshaobing.common.bo.sys.TableMeta;
import com.tom.dengshaobing.common.bo.wmp.json.Errorable;
import com.tom.dengshaobing.sqlstatements.SqlStatements;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月14日 上午11:31:23
 *
 */
@Service
public class CommonServiceImpl implements CommonService {

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	DataAccessService dataAccessService;

	@Override
	@Transactional
	public void logVisit(String visitorName) {
		String sql = "insert into LOG_VISIT(name, create_time) values(:name,:create_time)";
		Map<String, Object> sqlParamMap = new HashMap<String, Object>();
		sqlParamMap.put("name", visitorName);
		sqlParamMap.put("create_time", Calendar.getInstance());
		namedParameterJdbcTemplate.update(sql, sqlParamMap);

	}

	@Override
	public TableMeta listVisit() {
		return dataAccessService.queryTableMetaBySql(SqlStatements.get("002"), new HashMap<>());
	}

	@Override
	public void logErrorable(String uri, Errorable error) {
		String sql = "insert into LOG_ERRORABLE(uri, errorable, create_time) values(:uri,:errorable,:create_time)";
		Map<String, Object> sqlParamMap = new HashMap<String, Object>();
		sqlParamMap.put("uri", uri);
		sqlParamMap.put("errorable", error.toString());
		sqlParamMap.put("create_time", Calendar.getInstance());
		namedParameterJdbcTemplate.update(sql, sqlParamMap);
	}

}
