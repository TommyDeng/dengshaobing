package com.tom.dengshaobing.service.jerry;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tom.dengshaobing.common.bo.sys.TableMeta;
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

}
