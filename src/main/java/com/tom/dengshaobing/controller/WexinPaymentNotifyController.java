package com.tom.dengshaobing.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tom.dengshaobing.common.bo.wmp.xml.MessageXml;

import lombok.extern.slf4j.Slf4j;

/**
 * 微信支付回调
 * 
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年12月16日 下午3:44:53
 *
 */
@Slf4j
@RestController
@RequestMapping("/weixinPayment")
public class WexinPaymentNotifyController {

	@ResponseBody
	@RequestMapping(value = "/unifiedOrder", method = RequestMethod.POST)
	public MessageXml unifiedOrder(@RequestParam(value = "signature", required = false) String signature,
			@RequestParam(value = "timestamp", required = false) String timestamp,
			@RequestParam(value = "nonce", required = false) String nonce, @RequestBody MessageXml message)
			throws Exception {

		return null;
	}

}