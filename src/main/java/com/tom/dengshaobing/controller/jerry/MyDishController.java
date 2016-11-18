package com.tom.dengshaobing.controller.jerry;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tom.dengshaobing.controller.BaseController;
import com.tom.dengshaobing.service.CommonService;
import com.tom.dengshaobing.service.jerry.MyDishService;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年11月15日 上午11:09:52
 *
 */
@Controller
@RequestMapping("/jerry/dish")
public class MyDishController extends BaseController {
	public static final String BasePath = "/jerry/dish/";

	@Autowired
	CommonService commonService;
	
	@Autowired
	MyDishService myDishService;

	int recommendCount = 5;
	
	@RequestMapping("/main")
	public String main(@RequestParam(name = "openid", required = false) String openid, ModelMap map, String AT) throws Exception {

		if (StringUtils.isBlank(AT)) {
			AT = this.getAppToken(openid, "", commonService);
		}
		map.put(PxAT, AT);
		
		headerRending(AT, map);

		map.put("meatTableMeta", myDishService.getRecommendedMeatList(recommendCount));
		map.put("vegeTableMeta", myDishService.getRecommendedVegeList(recommendCount));
		
		Map<String,String> testMap = new HashMap<>();
		testMap.put("aaa", "123");
		map.put("testMap",testMap);
		
		return BasePath + "main";
	}
}
