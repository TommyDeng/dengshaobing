package com.tom.dengshaobing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tom.dengshaobing.service.CommonService;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月14日 上午11:29:38
 *
 */

@RestController
public class GreetingController {

	@Autowired
	CommonService commonService;

	@RequestMapping("/greeting")
	public String greeting(@RequestParam(value = "name", defaultValue = "anonymous") String name) throws Exception {
		commonService.logVisit(name);
		return "Hello " + name;
	}
}
