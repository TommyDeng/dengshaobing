package com.tom.dengshaobing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tom.dengshaobing.service.CommonService;


/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月14日 上午11:29:38
 *
 */

// @RestController
@Controller
public class GreetingController {

	@Autowired
	CommonService commonService;

	@RequestMapping("/greeting")
	public String greeting(ModelMap map) throws Exception {
		map.put("host", "Tommy");
		return "thmlf";
	}

	@RequestMapping("/visitorList")
	public String visitorList(ModelMap map) throws Exception {
		map.put("visitorList", commonService.listVisit());
		return "visitorList";
	}

	@RequestMapping("/detect-device")
	public @ResponseBody String detectDevice(Device device) {
		String deviceType = "unknown";
		if (device.isNormal()) {
			deviceType = "normal";
		} else if (device.isMobile()) {
			deviceType = "mobile";
		} else if (device.isTablet()) {
			deviceType = "tablet";
		}
		return "Hello " + deviceType + " browser!";
	}
}
