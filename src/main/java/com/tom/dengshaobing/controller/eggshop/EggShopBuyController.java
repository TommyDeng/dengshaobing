package com.tom.dengshaobing.controller.eggshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.spring4.view.ThymeleafView;

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

@Slf4j
@Controller
@RequestMapping("/eggshop/buy")
public class EggShopBuyController extends BaseController {
	public static final String BasePath = "/eggshop/buy/";

	@Autowired
	CommonService commonService;

	@Autowired
	EggShopBussService bussService;

	MapForm mapForm = new MapForm();

	public MapForm getMapForm() {
		return mapForm;
	}

	public void setMapForm(MapForm mapForm) {
		this.mapForm = mapForm;
	}

	@RequestMapping("/main")
	public String main(@RequestParam(name = "openid", required = false) String openid, ModelMap map) throws Exception {
		String appToken = this.getAppToken(openid, "", commonService);

		headerRending(appToken, map);
		map.put(PxAT, appToken);
		map.put(SxMapList, bussService.listAllProductForMain());
		return BasePath + "main";
	}

	@RequestMapping("/item")
	public String item(ModelMap map, String rowUC, String AT) throws Exception {

		return BasePath + "item";
	}

	@RequestMapping("/shoppingcart")
	public String shoppingcart(ModelMap map, String AT) throws Exception {
		map.put(PxAT, AT);

		TableMeta tableMeta = bussService.listOrder(null);
		tableMeta.title = "SHOPPING CART";
		map.put(SxTableMeta, tableMeta);
		return BasePath + "shoppingcart";
	}

	@RequestMapping("/checkout")
	public String checkout(@ModelAttribute MapForm mapForm, ModelMap map, String rowUC) throws Exception {

		return BasePath + "checkout";
	}

	@RequestMapping("/checkoutsubmit")
	public String checkoutsubmit(@ModelAttribute MapForm mapForm, ModelMap map, String rowUC) throws Exception {

		return "redirect:../product/list";
	}

}
