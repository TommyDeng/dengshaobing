package com.tom.dengshaobing.controller;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tom.dengshaobing.common.DefaultSetting;
import com.tom.dengshaobing.common.bo.wmp.Menu;
import com.tom.dengshaobing.common.bo.wmp.Message;
import com.tom.dengshaobing.service.WexinMessagePlatformService;
import com.tom.utils.JsonParseUtils;
import com.tom.utils.XMLParseUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月14日 上午11:29:38
 *
 */

@Slf4j
@RestController
public class WexinMessagePlatformController {
	@Autowired
	private Environment env;

	@Autowired
	WexinMessagePlatformService wexinMessagePlatformService;

	/**
	 * 接口配置
	 * 
	 * @param signature
	 * @param echostr
	 * @param timestamp
	 * @param nonce
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/restfull/wmp/access")
	public String wexinTokenAccess(@RequestParam(name = "signature", required = false) String signature,
			@RequestParam(name = "echostr", required = false) String echostr,
			@RequestParam(name = "timestamp", required = false) String timestamp,
			@RequestParam(name = "nonce", required = false) String nonce, @RequestBody(required = false) String body)
			throws Exception {

		String returnStr = "success";

		if (!wexinMessagePlatformService.checkSignature(signature, timestamp, nonce))
			return "validate failed...";

		// 判定此次请求为验证
		if (StringUtils.isBlank(body)) {
			return echostr;
		}
		// 其它判定为消息处理
		else {
			log.debug("/restfull/wmp/access body =======================>");
			log.debug(body);
			// xml 转 message 对象
			Message message = XMLParseUtils.generateJavaBean(body, Message.class);
			// 处理
			wexinMessagePlatformService.dispatch(message);

			// message 对象 转 xml
			returnStr = XMLParseUtils.generateXmlString(message);
		}

		return returnStr;
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