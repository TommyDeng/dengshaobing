package com.tom.dengshaobing.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tom.dengshaobing.common.bo.sys.TableMeta;
import com.tom.dengshaobing.common.bo.wmp.json.Errorable;
import com.tom.dengshaobing.service.eggshop.EggShopBussService;
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

	@Autowired
	EggShopBussService bussService;

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
	public String getAppTokenByEntranceId(String entranceId, String entranceType) throws Exception {
		// 简单实现,使用USER_UC作为token
		if (entranceId == null) {
			return entranceId;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("OPENID", entranceId);
		return dataAccessService.queryForOneObject("BUSS001", paramMap, UUID.class).toString();
	}

	@Override
	public UUID getUserUCByAppToken(String appToken) {
		// 暂时使用USER_UC作为token,所以直接返回UUID类型即可
		return UUID.fromString(appToken);
	}

	@Override
	public Map<String, Object> getWXUserInfo(String appToken) throws Exception {
		UUID userUC = getUserUCByAppToken(appToken);
		return dataAccessService.queryRowMapById("TX_USERINFO_WX", userUC);
	}
}
