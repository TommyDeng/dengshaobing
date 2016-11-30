package com.tom.dengshaobing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.tom.dengshaobing.service.CommonService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月14日 上午11:29:38
 *
 */

@Controller
@Slf4j
public class GreetingController extends BaseController {

	@Autowired
	CommonService commonService;

	@RequestMapping("/")
	public String index(ModelMap map,
			@RequestParam(name = "name", required = false, defaultValue = "anonymous") String name, Device device)
			throws Exception {
		String deviceType = "unknown";
		if (device.isNormal()) {
			deviceType = "normal";
		} else if (device.isMobile()) {
			deviceType = "mobile";
		} else if (device.isTablet()) {
			deviceType = "tablet";
		}

		map.put("host", name);
		commonService.logVisit(name, deviceType);
		return "index";
	}

	/*
	 * 关于
	 */
	@RequestMapping("/aboutme")
	public String aboutme(ModelMap map) throws Exception {

		// map.put("host", name);
		return "aboutme";
	}
	
	/*
	 * 版本简介
	 */
	@RequestMapping("/versiondetail")
	public String versiondetail(ModelMap map) throws Exception {
		// map.put("host", name);
		return "versiondetail";
	}
	
	/*
	 * 测试
	 */
	@RequestMapping("/test")
	public String test(ModelMap map) throws Exception {
		// map.put("host", name);
		return "test";
	}
}
