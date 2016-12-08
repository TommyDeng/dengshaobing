package com.tom.dengshaobing.controller.eggshop;

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
import com.tom.dengshaobing.controller.BaseController;
import com.tom.dengshaobing.service.eggshop.EggShopBussService;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月26日 上午10:19:18
 *
 */

@Controller
@RequestMapping("/eggshop/buy")
public class EggShopBuyController extends BaseController {
	public static final String BasePath = "/eggshop/buy/";

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
	public String pageInit(String AT, String openid, ModelMap map) throws Exception {
		super.pageInit(AT, openid, map);
		Map<String, Object> cartInfo = bussService.getShoppingCartInfo(AT);
		map.put("cartInfo", cartInfo);
		return AT;
	}

	@RequestMapping("/main")
	public String main(@RequestParam(name = "openid", required = false) String openid, ModelMap map, String AT)
			throws Exception {
		if (StringUtils.isEmpty(AT)) {
			AT = this.getAppToken(openid, "", commonService);
		}

		map.put(PxAT, AT);

		pageInit(AT, openid, map);

		map.put(SxMapList, bussService.listAllProductForMain());
		return BasePath + "main";
	}

	@RequestMapping("/item")
	public String item(@RequestParam(name = "openid", required = false) String openid, ModelMap map, String rowUC,
			String AT) throws Exception {
		map.put(PxAT, AT);

		pageInit(AT, openid, map);

		Map<String, Object> product = bussService.queryProduct(UUID.fromString(rowUC), AT);

		map.put("product", product);
		return BasePath + "item";
	}

	@RequestMapping("/addItem")
	@ResponseBody
	public String addItem(ModelMap map, String itemUC, String itemCount, String AT) throws Exception {
		Long shoppingCartCount = bussService.addItemShoppingCart(UUID.fromString(itemUC), Long.parseLong(itemCount),
				AT);
		return String.valueOf(shoppingCartCount);
	}

	@RequestMapping("/shoppingcart")
	public String shoppingcart(ModelMap map, String AT) throws Exception {
		map.put(PxAT, AT);

//		TableMeta tableMeta = bussService.listShoppingCart(AT);
//		tableMeta.title = "SHOPPING CART";

//		map.put(SxTableMeta, tableMeta);
		return BasePath + "shoppingcart";
	}

	@RequestMapping("/changeItemQty")
	@ResponseBody
	public String changeItemQty(ModelMap map, String cartItemUC, String itemCount, String AT) throws Exception {
		Long shoppingCartCount = bussService.changeCartItemQty(UUID.fromString(cartItemUC),
				Long.parseLong(itemCount), AT);
		return String.valueOf(shoppingCartCount);
	}

	@RequestMapping("/checkout")
	public String checkout(@ModelAttribute MapForm mapForm, ModelMap map, String AT) throws Exception {
		map.put(PxAT, AT);

		return BasePath + "checkout";
	}

	@RequestMapping("/checkoutsubmit")
	public String checkoutsubmit(@ModelAttribute MapForm mapForm, ModelMap map, String AT) throws Exception {
		map.put(PxAT, AT);

		return "redirect:../product/list";
	}

}
