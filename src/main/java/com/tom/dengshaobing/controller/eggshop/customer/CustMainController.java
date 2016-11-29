package com.tom.dengshaobing.controller.eggshop.customer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

		map.put("previousPage", "main");

		return BasePath + "main";
	}

	@RequestMapping("/category")
	public String category(@RequestParam(name = "openid", required = false) String openid, ModelMap map, String AT,
			String selectCategory) throws Exception {
		pageInit(AT, openid, map);

		if (StringUtils.isBlank(selectCategory)) {
			selectCategory = null;
		}

		map.put("categoryList", dataAccessService.queryMapList("ES_BUSS004"));

		Map<String, Object> parmMap = new HashMap<>();
		parmMap.put("CATEGORY", selectCategory);
		map.put("productList", dataAccessService.queryMapList("ES_BUSS005", parmMap));

		// selected category
		map.put("selectCategory", selectCategory);

		// to detail page param
		map.put("previousPage", "category");
		return BasePath + "category";
	}

	@RequestMapping("/itemdetail")
	public String itemdetail(@RequestParam(name = "openid", required = false) String openid, ModelMap map, String AT,
			String productUC, String previousPage, String previousCategory) throws Exception {
		pageInit(AT, openid, map);

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("UNIQUE_CODE", productUC);
		Map<String, Object> product = dataAccessService.queryForOneRowMap("ES_BUSS006", paramMap);

		map.put("product", product);

		Map<String, Object> cartInfo = bussService.getShoppingCartInfo(AT);
		map.put("cartInfo", cartInfo);
		
		
		//添加按钮 
		map.put("productUC", productUC);
		
		
		//返回按钮
		map.put("previousPage", previousPage);
		map.put("previousCategory", previousCategory);

		return BasePath + "itemdetail";
	}
	
	@RequestMapping("/addItem")
	@ResponseBody
	public String addItem(ModelMap map, String productUC, Long productCount, String AT) throws Exception {
		Long shoppingCartCount = bussService.addItemShoppingCart(UUID.fromString(productUC), productCount,
				AT);
		return String.valueOf(shoppingCartCount);
	}

	@RequestMapping("/cart")
	public String cart(@RequestParam(name = "openid", required = false) String openid, ModelMap map, String AT)
			throws Exception {
		pageInit(AT, openid, map);

		TableMeta tableMeta = bussService.listShoppingCart(AT);
		tableMeta.title = "SHOPPING CART";

		map.put(SxTableMeta, tableMeta);
		
		
		map.put("previousPage", "cart");
		
		return BasePath + "cart";
	}

	@RequestMapping("/preorder")
	public String preorder(@RequestParam(name = "openid", required = false) String openid, ModelMap map, String AT)
			throws Exception {
		pageInit(AT, openid, map);

		return BasePath + "preorder";
	}

	@RequestMapping("/myprofile")
	public String myprofile(@RequestParam(name = "openid", required = false) String openid, ModelMap map, String AT)
			throws Exception {
		pageInit(AT, openid, map);

		map.put("weixinUserInfo", bussService.getWeixinUserInfo(AT));
		map.put("contactInfo", bussService.getUserInfo(AT));
		return BasePath + "myprofile";
	}

}
