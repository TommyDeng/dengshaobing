package com.tom.dengshaobing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tom.dengshaobing.service.WexinMessagePlatformService;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月14日 上午11:29:38
 *
 */

@RestController
public class WexinMessagePlatformController {

	@Autowired
	WexinMessagePlatformService wexinMessagePlatformService;
	/**
	 * 验证服务器地址的有效性
	 * @param signature
	 * @param echostr
	 * @param timestamp
	 * @param nonce
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/validateServer")
	public String wexinTokenAccess(@RequestParam(name = "signature", required = false) String signature,
			@RequestParam(name = "echostr", required = false) String echostr,
			@RequestParam(name = "timestamp", required = false) String timestamp,
			@RequestParam(name = "nonce", required = false) String nonce) throws Exception {
		if(wexinMessagePlatformService.checkSignature(signature, timestamp, nonce)){
			
		}
		return echostr;
	}

}