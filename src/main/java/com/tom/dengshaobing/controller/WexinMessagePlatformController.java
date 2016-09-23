package com.tom.dengshaobing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tom.dengshaobing.common.bo.wmp.xml.MessageXml;
import com.tom.dengshaobing.service.WexinMessagePlatformService;
import com.tom.utils.XMLParseUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 基础平台请求处理
 * 
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月14日 上午11:29:38
 *
 */

@Slf4j
@RestController
public class WexinMessagePlatformController {

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
	@RequestMapping(value = "/restfull/wmp/access", method = RequestMethod.GET)
	public String wexinTokenAccess(@RequestParam(name = "signature", required = false) String signature,
			@RequestParam(name = "echostr", required = false) String echostr,
			@RequestParam(name = "timestamp", required = false) String timestamp,
			@RequestParam(name = "nonce", required = false) String nonce) throws Exception {
		// 验证
		if (!wexinMessagePlatformService.checkSignature(signature, timestamp, nonce))
			return null;
		log.info("/restfull/wmp/access validate success =======================>");
		log.info(XMLParseUtils.generateXmlString(echostr));
		// GET请求均判定为服务器绑定认证
		return echostr;

	}

	@RequestMapping(value = "/restfull/wmp/access", method = RequestMethod.POST)
	public MessageXml wexinMessageAccess(@RequestParam(value = "signature", required = false) String signature,
			@RequestParam(value = "timestamp", required = false) String timestamp,
			@RequestParam(value = "nonce", required = false) String nonce, @RequestBody MessageXml message)
			throws Exception {
		// 验证
		if (!wexinMessagePlatformService.checkSignature(signature, timestamp, nonce))
			return null;

		// POST请求均判定为消息处理
		log.info("/restfull/wmp/access body =======================>");
		log.info(XMLParseUtils.generateXmlString(message));

		MessageXml returnMessage = wexinMessagePlatformService.dispatch(message);

		log.info("/restfull/wmp/access return =======================>");
		log.info(XMLParseUtils.generateXmlString(returnMessage));
		return returnMessage;
	}

}