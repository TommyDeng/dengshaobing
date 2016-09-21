package com.tom.dengshaobing.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tom.dengshaobing.common.DefaultSetting;
import com.tom.dengshaobing.common.bo.wmp.Menu;
import com.tom.dengshaobing.service.WexinMessagePlatformService;
import com.tom.utils.JsonParseUtils;
import com.tom.utils.ProjectConfigUtils;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月14日 上午11:29:38
 *
 */

@RestController
public class WexinMessagePlatformController {
	@Autowired
	private Environment env;

	@Autowired
	WexinMessagePlatformService wexinMessagePlatformService;

	/**
	 * 验证服务器地址的有效性
	 * 
	 * @param signature
	 * @param echostr
	 * @param timestamp
	 * @param nonce
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/restfull/wmp/validateServer")
	public String wexinTokenAccess(@RequestParam(name = "signature", required = false) String signature,
			@RequestParam(name = "echostr", required = false) String echostr,
			@RequestParam(name = "timestamp", required = false) String timestamp,
			@RequestParam(name = "nonce", required = false) String nonce) throws Exception {
		if (wexinMessagePlatformService.checkSignature(signature, timestamp, nonce)) {
			return echostr;
		}
		return "validate failed...";
	}

	@RequestMapping("/restfull/wmp/refreshMenu")
	public String refreshMenu() throws Exception {
		wexinMessagePlatformService.deleteMenu();
		InputStream inputStream = Environment.class
				.getResourceAsStream(env.getProperty("WeixinPlatform.MenuJsonFilePath"));

		String menuJsonStr = IOUtils.toString(inputStream, DefaultSetting.CHARSET);
		IOUtils.closeQuietly(inputStream);

		// convert to compact json string
		Menu menu = JsonParseUtils.generateJavaBean(menuJsonStr, Menu.class);
		menuJsonStr = JsonParseUtils.generateJsonString(menu);

		wexinMessagePlatformService.createMenu(menuJsonStr);
		return "refresh finished..";
	}

}