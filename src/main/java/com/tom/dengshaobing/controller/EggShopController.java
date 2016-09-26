package com.tom.dengshaobing.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
public class EggShopController {

	@Autowired
	CommonService commonService;

	@RequestMapping("/buy")
	public String buy(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {

		map.put("host", request.getParameter("openid"));
		log.info("===================buy invoked===============================");
		Enumeration<String> paramNameEnum = request.getParameterNames();
		for (String parmName; paramNameEnum.hasMoreElements();) {
			parmName = paramNameEnum.nextElement().toString();
			String thisValue = request.getParameter(parmName);
			log.info(parmName + "--------------" + thisValue);
		}

		return "thmlf";
	}

	@RequestMapping("/list")
	public String list(ModelMap map) throws Exception {
		map.put("host", "");

		return "thmlf";
	}

}
