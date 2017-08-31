package com.tom.dengshaobing.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tom.dengshaobing.common.Const;
import com.tom.dengshaobing.common.bo.wmp.xml.weixinpayment.NotifyRequestXml;
import com.tom.dengshaobing.common.bo.wmp.xml.weixinpayment.NotifyResponseXml;
import com.tom.dengshaobing.service.DataAccessService;
import com.tom.utils.PaymentSignUtils;
import com.tom.utils.XMLParseUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 微信支付回调
 * 
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年12月16日 下午3:44:53
 *
 */
@Slf4j
@Controller
public class WexinPaymentController extends BaseController {

	@Autowired
	private Environment env;

	@Autowired
	DataAccessService dataAccessService;

	@ResponseBody
	@RequestMapping(value = "/eggshop/payment/customerPayOrderNotify", method = RequestMethod.POST)
	public String customerPayOrderNotify(@RequestParam(value = "signature", required = false) String signature,
			@RequestParam(value = "timestamp", required = false) String timestamp,
			@RequestParam(value = "nonce", required = false) String nonce, @RequestBody NotifyRequestXml message)
			throws Exception {

		log.info("******************** customerPayOrderNotify result notify recieved ********************");
		log.info(message.toString());
		log.info("******************** customerPayOrderNotify result notify recieved ********************");

		if ("SUCCESS".equals(message.return_code) || "SUCCESS".equals(message.result_code)) {
			// 更新订单状态
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("OUT_TRADE_NO", message.out_trade_no);
			paramMap.put("STATUS", Const.ORDER_STATUS.WaitToRecieve);
			dataAccessService.update("ES_BUSS026", paramMap);
		}

		NotifyResponseXml response = new NotifyResponseXml();
		response.return_code = "SUCCESS";
		response.return_msg = "OK";

		return XMLParseUtils.generateXmlString(response);
	}

	@RequestMapping("/eggshop/payment/customerPayOrder")
	public String customerPayOrder(ModelMap map, String AT, String orderUC) throws Exception {

		map.put(PxAT, AT);

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("UNIQUE_CODE", UUID.fromString(orderUC));
		Map<String, Object> resultMap = dataAccessService.queryForMap("ES_BUSS025", paramMap);

		// JSAPI
		Map<String, String> prepayInfo = new HashMap<>();
		prepayInfo.put("appId", env.getProperty("WeixinPlatform.AppID"));
		prepayInfo.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
		prepayInfo.put("nonceStr", UUID.randomUUID().toString().replaceAll("-", ""));
		prepayInfo.put("package", "prepay_id=" + resultMap.get("PREPAY_ID"));
		prepayInfo.put("signType", "MD5");
		prepayInfo.put("paySign", PaymentSignUtils.sign(prepayInfo, env.getProperty("WeixinPlatform.Payment.Key")));

		map.put("prepayInfo", prepayInfo);

		return "/eggshop/payment/customerPayOrder";
	}
}