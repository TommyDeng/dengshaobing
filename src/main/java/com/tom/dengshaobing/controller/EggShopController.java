package com.tom.dengshaobing.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tom.dengshaobing.common.bo.sys.MapForm;
import com.tom.dengshaobing.common.bo.sys.TableMeta;
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
@RequestMapping("/eggshop")
public class EggShopController extends BaseController {

	@Autowired
	CommonService commonService;

	@Autowired
	EggShopBussService eggShopBussService;

	MapForm mapForm = new MapForm();
	
	@RequestMapping("/product/init")
	public String productInit(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "openid", required = false) String openid, ModelMap map) throws Exception {
		// 查询用户userUC
		String userUC = (String) map.get(PxOpenid);
		log.info("userUC:" + userUC);

		
		map.put(SxFormData, mapForm);

		return "eggshop/product/product";
	}

	@RequestMapping("/product/add")
	public String productAdd(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name = "openid", required = false) String openid, @ModelAttribute MapForm mapForm,
			ModelMap map) throws Exception {
		// 查询用户userUC
		UUID userUC = eggShopBussService.getUserUCByOpenid(openid);
		eggShopBussService.addProduct(mapForm.getProperties(), userUC);
		return "redirect:list";
	}

	@RequestMapping("/product/list")
	public String productList(@RequestParam(name = "openid", required = false) String openid, ModelMap map)
			throws Exception {
		UUID userUC = eggShopBussService.getUserUCByOpenid(openid);
		
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
		UUID userUC = eggShopBussService.getUserUCByOpenid(openid);

		return "eggshop/buy";
	}

	@RequestMapping("/buy/add")
	public String buyAdd(@RequestParam(name = "openid", required = false) String openid, ModelMap map)
			throws Exception {
		// 查询用户userUC
		UUID userUC = eggShopBussService.getUserUCByOpenid(openid);

		return "eggshop/buy";
	}

	@RequestMapping("/list/init")
	public String listInit(@RequestParam(name = "openid", required = false) String openid, ModelMap map)
			throws Exception {
		// 查询用户userUC
		UUID userUC = eggShopBussService.getUserUCByOpenid(openid);

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
