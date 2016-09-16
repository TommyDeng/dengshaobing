package com.tom.dengshaobing.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月14日 上午11:31:23
 *
 */
@Service
public class CommonServiceImpl implements CommonService {

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Transactional
	public void logVisit(String visitorName) {
		Map<String, Object> sqlParamMap = new HashMap<String, Object>();
		sqlParamMap.put("name", visitorName);
		sqlParamMap.put("create_time", Calendar.getInstance());
		namedParameterJdbcTemplate.update("insert into LOG_VISIT(name, create_time) values(:name,:create_time)",
				sqlParamMap);

	}
}
