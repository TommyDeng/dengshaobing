package com.tom.dengshaobing.controller.eggshop.customer;

import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tom.dengshaobing.common.bo.sys.MapForm;
import com.tom.dengshaobing.common.bo.sys.TableMeta;
import com.tom.dengshaobing.controller.BaseController;
import com.tom.dengshaobing.service.CommonService;
import com.tom.dengshaobing.service.eggshop.EggShopBussService;

import lombok.extern.slf4j.Slf4j;

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

	MapForm mapForm = new MapForm();

	public MapForm getMapForm() {
		return mapForm;
	}

	public void setMapForm(MapForm mapForm) {
		this.mapForm = mapForm;
	}

	// 加载用户信息和count
	@Override
	public void headerRending(String AT, ModelMap map) throws Exception {
		super.headerRending(AT, map);
		Map<String, Object> cartInfo = bussService.getShoppingCartInfo(AT);
		map.put("cartInfo", cartInfo);
	}

	@RequestMapping("/main")
	public String main(@RequestParam(name = "openid", required = false) String openid, ModelMap map, String AT)
			throws Exception {
		if (StringUtils.isBlank(AT)) {
			AT = this.getAppToken(openid, "", commonService);
		}
		map.put(PxAT, AT);
		map.put("testCss", "3");
		headerRending(AT, map);

		map.put(SxMapList, bussService.listAllProductForMain());
		return BasePath + "main";
	}

	@RequestMapping("/category")
	public String category(@RequestParam(name = "openid", required = false) String openid, ModelMap map, String AT)
			throws Exception {
		if (StringUtils.isBlank(AT)) {
			AT = this.getAppToken(openid, "", commonService);
		}

		map.put(PxAT, AT);
		headerRending(AT, map);

		map.put(SxMapList, bussService.listAllProductForMain());
		return BasePath + "category";
	}

	@RequestMapping("/cart")
	public String cart(@RequestParam(name = "openid", required = false) String openid, ModelMap map, String AT)
			throws Exception {

		if (StringUtils.isBlank(AT)) {
			AT = this.getAppToken(openid, "", commonService);
		}

		map.put(PxAT, AT);
		headerRending(AT, map);

		TableMeta tableMeta = bussService.listShoppingCart(AT);
		tableMeta.title = "SHOPPING CART";

		map.put(SxTableMeta, tableMeta);
		return BasePath + "cart";
	}

	@RequestMapping("/myprofile")
	public String myprofile(@RequestParam(name = "openid", required = false) String openid, ModelMap map, String AT)
			throws Exception {
		if (StringUtils.isBlank(AT)) {
			AT = this.getAppToken(openid, "", commonService);
		}

		map.put(PxAT, AT);
		headerRending(AT, map);
		return BasePath + "myprofile";
	}

}
