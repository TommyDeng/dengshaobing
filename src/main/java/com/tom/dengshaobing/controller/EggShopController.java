package com.tom.dengshaobing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tom.dengshaobing.common.bo.sys.TableMeta;
import com.tom.dengshaobing.service.CommonService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月26日 上午10:19:18
 *
 */
@Controller
@Slf4j
@RequestMapping("/eggshop")
public class EggShopController extends BaseController {

	@Autowired
	CommonService commonService;

	// 点击购买
	@RequestMapping("/buy")
	public String buy(@RequestParam(name = "openid", required = false) String openId, ModelMap map) throws Exception {
		log.info("===================buy invoked===============================");
		map.put("host", openId);

		return "eggshop/buy";
	}

	// 已购清单
	@RequestMapping("/list")
	public String list(@RequestParam(name = "openid", required = false) String openId, ModelMap map) throws Exception {
		log.info("===================list invoked===============================");
		TableMeta tableMeta = commonService.listVisit();
		tableMeta.title = "Recent Visitor:";

		map.put(SxTableMeta, tableMeta);
		return "eggshop/list";
	}

	// 点击 myprofile
	@RequestMapping("/myprofile")
	public String myprofile(@RequestParam(name = "openid", required = false) String openId, ModelMap map)
			throws Exception {
		log.info("===================myprofile invoked===============================");
		map.put("visitorList", commonService.listVisit());
		return "eggshop/myprofile";
	}
}
