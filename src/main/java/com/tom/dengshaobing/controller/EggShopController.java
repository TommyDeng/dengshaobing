package com.tom.dengshaobing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tom.dengshaobing.common.bo.sys.TableMeta;
import com.tom.dengshaobing.service.CommonService;
import com.tom.dengshaobing.service.eggshop.EggShopBussService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月26日 上午10:19:18
 *
 */
@Controller
@RequestMapping("/eggshop")
public class EggShopController extends BaseController {

	@Autowired
	CommonService commonService;

	@Autowired
	EggShopBussService eggShopBussService;

	@RequestMapping("/product/init")
	public String productInit(@RequestParam(name = "openid", required = false) String openid, ModelMap map)
			throws Exception {
		// 查询用户userUC
		String userUC = eggShopBussService.getUserUCByOpenid(openid);
		
		
		return "eggshop/product/productList";
	}

	@RequestMapping("/product/add")
	public String productAdd(@RequestParam(name = "openid", required = false) String openid, ModelMap map)
			throws Exception {
		// 查询用户userUC
		String userUC = eggShopBussService.getUserUCByOpenid(openid);

		return "eggshop/buy";
	}

	@RequestMapping("/product/list")
	public String productList(@RequestParam(name = "openid", required = false) String openid, ModelMap map)
			throws Exception {

		// 查询所有产品
		TableMeta tableMeta = eggShopBussService.listAllProduct();
		tableMeta.title = "product";
		map.put(SxTableMeta, tableMeta);

		return "eggshop/product/productList";
	}

	@RequestMapping("/buy/init")
	public String buyInit(@RequestParam(name = "openid", required = false) String openid, ModelMap map)
			throws Exception {
		// 查询用户userUC
		String userUC = eggShopBussService.getUserUCByOpenid(openid);

		return "eggshop/buy";
	}

	@RequestMapping("/buy/add")
	public String buyAdd(@RequestParam(name = "openid", required = false) String openid, ModelMap map)
			throws Exception {
		// 查询用户userUC
		String userUC = eggShopBussService.getUserUCByOpenid(openid);

		return "eggshop/buy";
	}

	@RequestMapping("/list/init")
	public String listInit(@RequestParam(name = "openid", required = false) String openid, ModelMap map) throws Exception {
		// 查询用户userUC
		String userUC = eggShopBussService.getUserUCByOpenid(openid);

		// 查询用户order
		TableMeta tableMeta = eggShopBussService.listOrderByUserUC(userUC);
		tableMeta.title = "Order";
		map.put(SxTableMeta, tableMeta);
		return "eggshop/list";
	}

	@RequestMapping("/myprofile/init")
	public String myprofile(@RequestParam(name = "openid", required = false) String openid, ModelMap map)
			throws Exception {
		map.put("visitorList", commonService.listVisit());
		return "eggshop/myprofile";
	}
}
