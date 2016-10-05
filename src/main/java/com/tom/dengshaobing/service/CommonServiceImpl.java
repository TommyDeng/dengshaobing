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
	public void logVisit(String visitorName, String deviceType) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name".toUpperCase(), visitorName);
		paramMap.put("remark".toUpperCase(), deviceType);
		dataAccessService.insertSingle("LOG_VISIT", paramMap);
	}

	@Override
	public TableMeta listVisit() {
		return dataAccessService.queryTableMeta(SqlStatements.get("002"), new HashMap<>());
	}

	@Override
	public void logErrorable(String uri, Errorable error) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("uri".toUpperCase(), uri);
		paramMap.put("errorable".toUpperCase(), error.toString());
		paramMap.put("create_time".toUpperCase(), Calendar.getInstance());
		dataAccessService.insertSingle("LOG_ERRORABLE", paramMap);
	}

	@Override
	public String getAppToken(String entranceId) {
		// 暂无实现,使用传入值作为唯一标示
		return entranceId;
	}

}
