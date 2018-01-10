package com.tom.dengshaobing.controller.eggshop.operator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tom.dengshaobing.controller.BaseController;
import com.tom.dengshaobing.service.DataAccessService;
import com.tom.dengshaobing.service.eggshop.EggShopBussService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月26日 上午10:19:18
 *
 */

@Slf4j
@Controller
@RequestMapping("/eggshop/operator")
public class OperatorOrderController extends BaseController {
	public static final String BasePath = "/eggshop/operator/";

	@Autowired
	EggShopBussService bussService;

	@Autowired
	DataAccessService dataAccessService;

	@RequestMapping("/orderList")
	public String orderList(@RequestParam(name = "visitId", required = false) String visitId,
			@RequestParam(name = "visitType", required = false) String visitType, ModelMap map, String AT)
			throws Exception {
		AT = pageInit(AT, visitId, visitType, map);

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("USER_UC", null);
		paramMap.put("STATUS", null);

		List<Map<String, Object>> orderList = dataAccessService.queryMapList("ES_BUSS019", paramMap);

		map.put("orderList", orderList);

		return BasePath + "orderList";
	}
	
	@RequestMapping("/orderItemDetail")
	public String orderItemDetail(String orderUC, ModelMap map, String AT)
			throws Exception {
		// 订单明细项
		Map<String, Object> itemQueryParamMap = new HashMap<>();
		itemQueryParamMap.put("ORDER_UC", orderUC);
		List<Map<String, Object>> orderItemList = dataAccessService.queryMapList("ES_BUSS020",
				itemQueryParamMap);
		map.put("itemList", orderItemList);

		return BasePath + "orderList :: #detailsModalDataTable";
	}
	
}
