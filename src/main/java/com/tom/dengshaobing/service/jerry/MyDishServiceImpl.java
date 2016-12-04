package com.tom.dengshaobing.service.jerry;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tom.dengshaobing.common.bo.sys.TableMeta;
import com.tom.dengshaobing.service.CommonService;
import com.tom.dengshaobing.service.DataAccessService;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年11月15日 下午2:58:02
 *
 */
@Service
public class MyDishServiceImpl implements MyDishService {

	@Autowired
	DataAccessService dataAccessService;
	@Autowired
	CommonService commonService;

	@Override
	public TableMeta listAllCookbook() {
		return dataAccessService.queryTableMeta("JR_BUSS002", null);
	}

	@Override
	public TableMeta getRecommendedMeatList(int recommendCount) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("TYPE", "MEAT");
		paramMap.put("count", recommendCount);
		return dataAccessService.queryTableMeta("JR_BUSS001", paramMap);
	}

	@Override
	public TableMeta getRecommendedVegeList(int recommendCount) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("TYPE", "VEGE");
		paramMap.put("count", recommendCount);
		return dataAccessService.queryTableMeta("JR_BUSS001", paramMap);
	}

	@Override
	public Map<String, Object> queryCookbook(UUID UC, String AT) throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("UNIQUE_CODE", UC);
		return dataAccessService.queryForOneRow("JR_BUSS003", paramMap);
	}

	@Override
	public void addCookbook(Map<String, Object> properties, String AT) throws Exception {
		UUID userUC = commonService.getUserUCByAppToken(AT);
		properties.put("UNIQUE_CODE", UUID.randomUUID().toString());
		properties.put("CREATOR", String.valueOf(userUC));
		properties.put("ENABLE", true);
		dataAccessService.insertSingle("JR_COOKBOOK", properties);
	}

	@Override
	public void deleteCookbook(UUID UC, String AT) throws Exception {
		dataAccessService.deleteRowById("JR_COOKBOOK", UC);
	}

	@Override
	public void updateCookbook(Map<String, Object> properties, String AT) throws Exception {
		dataAccessService.updateSingle("JR_COOKBOOK", properties);
	}

	@Override
	public void listAllCookbook(String AT) {
		dataAccessService.queryTableMeta("JR_BUSS002", null);
	}
}
