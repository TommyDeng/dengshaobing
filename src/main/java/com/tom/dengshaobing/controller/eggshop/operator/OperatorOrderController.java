package com.tom.dengshaobing.controller.eggshop.operator;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.tom.dengshaobing.common.bo.sys.ListForm;
import com.tom.dengshaobing.common.bo.sys.MapForm;
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
	public String orderList(@RequestParam(name = "openid", required = false) String openid, ModelMap map, String AT)
			throws Exception {
		AT = pageInit(AT, openid, map);
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("USER_UC", null);
		paramMap.put("STATUS", null);
		map.put("orderList", dataAccessService.queryMapList("ES_BUSS019", paramMap));

		return BasePath + "orderList";
	}

}
