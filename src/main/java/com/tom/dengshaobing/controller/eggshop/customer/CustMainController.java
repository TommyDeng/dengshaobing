package com.tom.dengshaobing.controller.eggshop.customer;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tom.dengshaobing.common.bo.sys.MapForm;
import com.tom.dengshaobing.common.bo.sys.TableMeta;
import com.tom.dengshaobing.controller.BaseController;
import com.tom.dengshaobing.service.DataAccessService;
import com.tom.dengshaobing.service.eggshop.EggShopBussService;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月26日 上午10:19:18
 *
 */

@Controller
@RequestMapping("/eggshop/customer")
public class CustMainController extends BaseController {
	public static final String BasePath = "/eggshop/customer/";

	@Autowired
	EggShopBussService bussService;

	@Autowired
	DataAccessService dataAccessService;

	MapForm mapForm = new MapForm();

	public MapForm getMapForm() {
		return mapForm;
	}

	public void setMapForm(MapForm mapForm) {
		this.mapForm = mapForm;
	}

	@RequestMapping("/main")
	public String main(@RequestParam(name = "openid", required = false) String openid, ModelMap map, String AT)
			throws Exception {
		pageInit(AT, openid, map);
		map.put("swiperList", dataAccessService.queryMapList("ES_BUSS001"));
		map.put("hotList", dataAccessService.queryMapList("ES_BUSS002"));
		map.put("recentList", dataAccessService.queryMapList("ES_BUSS003"));

		map.put("previous", "main");

		return BasePath + "main";
	}

	@RequestMapping("/category")
	public String category(@RequestParam(name = "openid", required = false) String openid, ModelMap map, String AT,
			String categoryUC) throws Exception {
		pageInit(AT, openid, map);

		map.put("categoryList", dataAccessService.queryMapList("ES_BUSS004"));

		Map<String, Object> parmMap = new HashMap<>();
		parmMap.put("CATEGORY", categoryUC);
		map.put("productList", dataAccessService.queryMapList("ES_BUSS005", parmMap));

		// selected category
		map.put("categoryUC", categoryUC);

		map.put("previous", "category");

		return BasePath + "category";
	}

	@RequestMapping("/cart")
	public String cart(@RequestParam(name = "openid", required = false) String openid, ModelMap map, String AT)
			throws Exception {

		if (StringUtils.isBlank(AT)) {
			AT = this.getAppToken(openid, "", commonService);
		}

		map.put(PxAT, AT);
		pageInit(AT, openid, map);

		TableMeta tableMeta = bussService.listShoppingCart(AT);
		tableMeta.title = "SHOPPING CART";

		map.put(SxTableMeta, tableMeta);
		return BasePath + "cart";
	}

	@RequestMapping("/preorder")
	public String preorder(@RequestParam(name = "openid", required = false) String openid, ModelMap map, String AT)
			throws Exception {

		if (StringUtils.isBlank(AT)) {
			AT = this.getAppToken(openid, "", commonService);
		}

		map.put(PxAT, AT);
		pageInit(AT, openid, map);

		return BasePath + "preorder";
	}

	@RequestMapping("/myprofile")
	public String myprofile(@RequestParam(name = "openid", required = false) String openid, ModelMap map, String AT)
			throws Exception {
		if (StringUtils.isBlank(AT)) {
			AT = this.getAppToken(openid, "", commonService);
		}

		map.put(PxAT, AT);

		pageInit(AT, openid, map);

		map.put("weixinUserInfo", bussService.getWeixinUserInfo(AT));
		map.put("contactInfo", bussService.getUserInfo(AT));
		return BasePath + "myprofile";
	}

	@RequestMapping("/itemdetail")
	public String item(@RequestParam(name = "openid", required = false) String openid, ModelMap map, String AT,
			String productUC, String previous) throws Exception {
		if (StringUtils.isBlank(AT)) {
			AT = this.getAppToken(openid, "", commonService);
		}

		map.put(PxAT, AT);
		map.put("previous", previous);
		pageInit(AT, openid, map);

		return BasePath + "itemdetail";
	}
}
