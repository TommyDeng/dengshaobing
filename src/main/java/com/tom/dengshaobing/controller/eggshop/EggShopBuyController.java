package com.tom.dengshaobing.controller.eggshop;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
		UUID userUC = bussService.getUserUCByOpenid(openid);

		map.put(SxMapList, bussService.listAllProductForMain());

		return BasePath + "main";
	}

	@RequestMapping("/item")
	public String item(HttpServletRequest request, HttpServletResponse response, ModelMap map, String rowUC)
			throws Exception {

		return BasePath + "item";
	}

	@RequestMapping("/shoppingcart")
	public String shoppingcart(HttpServletRequest request, HttpServletResponse response, ModelMap map, String rowUC)
			throws Exception {
		TableMeta tableMeta = bussService.listOrderByUserUC(null);
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
