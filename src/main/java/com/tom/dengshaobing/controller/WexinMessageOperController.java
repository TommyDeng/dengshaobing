package com.tom.dengshaobing.controller;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tom.dengshaobing.common.DefaultSetting;
import com.tom.dengshaobing.common.bo.wmp.xml.Menu;
import com.tom.dengshaobing.service.WexinMessagePlatformService;
import com.tom.utils.JsonParseUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月23日 上午10:40:16
 *
 */
@Slf4j
@RestController
public class WexinMessageOperController {
	@Autowired
	private Environment env;

	@Autowired
	WexinMessagePlatformService wexinMessagePlatformService;

	/**
	 * 用json文件重新装载菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/restfull/wmp/refreshMenu")
	public String refreshMenu() throws Exception {

		log.info("/restfull/wmp/refreshMenu refresh start =======================>");

		wexinMessagePlatformService.deleteMenu();

		log.info("/restfull/wmp/refreshMenu menu deleted =======================>");

		String menuFilePath = env.getProperty("WeixinPlatform.MenuJsonFilePath");
		InputStream inputStream = Environment.class.getResourceAsStream(menuFilePath);

		log.info("/restfull/wmp/refreshMenu menu using file:" + menuFilePath + " =======================>");

		String menuJsonStr = IOUtils.toString(inputStream, DefaultSetting.CHARSET);
		IOUtils.closeQuietly(inputStream);

		// convert to compact json string
		Menu menu = JsonParseUtils.generateJavaBean(menuJsonStr, Menu.class);
		menuJsonStr = JsonParseUtils.generateJsonString(menu);

		wexinMessagePlatformService.createMenu(menuJsonStr);

		log.info("/restfull/wmp/refreshMenu refresh end =======================>");

		return "refresh menu successfull...";
	}
}
