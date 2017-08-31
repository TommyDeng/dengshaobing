package com.tom.dengshaobing.service;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.tom.dengshaobing.common.Const;
import com.tom.dengshaobing.common.DefaultSetting;
import com.tom.dengshaobing.common.bo.wmp.xml.weixinpayment.UnifiedOrderRequestXml;
import com.tom.dengshaobing.common.bo.wmp.xml.weixinpayment.UnifiedOrderResponseXml;
import com.tom.utils.DateTimeUtils;
import com.tom.utils.PaymentSignUtils;
import com.tom.utils.XMLParseUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年12月16日 上午11:50:03
 *
 */
@Slf4j
@Service
public class WexinPaymentServiceImpl implements WexinPaymentService {

	@Autowired
	private Environment env;

	@Autowired
	CommonService commonService;

	@Autowired
	DataAccessService dataAccessService;

	@Autowired
	WmpEventService wmpBussService;

	@Autowired
	HttpProcessSerice httpProcessSerice;

	@Override
	public void unifiedOrder(UUID orderUC, String ipAddress, String AT) throws Exception {
		log.info("unifiedOrder start ====================>");
		log.info("<param print start>");
		log.info("orderUC:" + orderUC.toString());
		log.info("ipAddress:" + ipAddress);
		log.info("AT:" + AT);
		log.info("<param print end>");
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("UNIQUE_CODE", orderUC);
		
		Map<String, Object> orderRow = dataAccessService.queryForMapAllColumn("ES_ORDER", paramMap);

		UUID paymentUC = UUID.randomUUID();

		UnifiedOrderRequestXml request = new UnifiedOrderRequestXml();
		// 公众账号ID
		request.setAppid(env.getProperty("WeixinPlatform.AppID"));
		// 商户号
		request.setMch_id(env.getProperty("WeixinPlatform.MerchantID"));
		// 设备号
		request.setDevice_info("WEB");
		// 随机字符串
		request.setNonce_str(UUID.randomUUID().toString().replaceAll("-", ""));

		// 签名类型 默认使用MD5加密
		request.setSign_type("MD5");

		// 商品描述 商家名称-销售商品类目
		request.setBody("乐果农场-产品购买");

		// 商品详情
		// request.detail= "商品详情";

		// 附加数据
		// request.attach="微信公众号分店";

		// 商户订单号 当前时间戳+当前线程ID
		String tradeNo = DateTimeUtils.getCurrentFormatString("yyyyMMddHHmmssSSS")
				+ String.valueOf(Thread.currentThread().getId());
		request.setOut_trade_no(tradeNo);

		// 标价币种
		request.setFee_type("CNY");

		// 标价金额 单位为分
		BigDecimal totalAmt = (BigDecimal) orderRow.get("TOTAL_AMT");
		String totalAmtCent = totalAmt.movePointRight(2).toString();
		request.setTotal_fee(totalAmtCent);

		// 终端IP
		request.setSpbill_create_ip(ipAddress);

		Calendar orderApplyCal = Calendar.getInstance();
		// 交易起始时间 订单生成时间
		request.setTime_start(DateTimeUtils.getFormatStringByDate(orderApplyCal.getTime(), "yyyyMMddHHmmss"));

		Calendar orderExpireCal = Calendar.getInstance();
		orderExpireCal.add(Calendar.DAY_OF_YEAR, 1);// 暂定为一天后失效
		// 交易结束时间 订单失效时间
		request.setTime_expire(DateTimeUtils.getFormatStringByDate(orderExpireCal.getTime(), "yyyyMMddHHmmss"));

		// 商品标记
		// request.goods_tag= "WXG";

		// 通知地址
		request.setNotify_url(env.getProperty("WeixinPlatform.Payment.AuthRoot") + "customerPayOrderNotify");

		// 交易类型
		request.setTrade_type("JSAPI");

		// 商品ID product_id
		// 指定支付方式 limit_pay

		// 用户标识 openid
		Map<String, Object> wxUserInfo = commonService.getWXUserInfo(AT);
		String openId = (String) wxUserInfo.get("OPENID");
		request.setOpenid(openId);

		PaymentSignUtils.sign(request, env.getProperty("WeixinPlatform.Payment.Key"));

		// URL地址：https://api.mch.weixin.qq.com/pay/unifiedorder
		URI uri = new URIBuilder("https://api.mch.weixin.qq.com/pay/unifiedorder").setCharset(DefaultSetting.CHARSET)
				.build();
		String requestXml = XMLParseUtils.generateXmlString(request);
		log.info("request xml ====================>");
		log.info(XMLParseUtils.formatXMLStr(requestXml));
		String responseXml = httpProcessSerice.httpPost(uri, requestXml);
		log.info("response xml ====================>");
		log.info(XMLParseUtils.formatXMLStr(responseXml));

		UnifiedOrderResponseXml response = XMLParseUtils.generateJavaBean(responseXml, UnifiedOrderResponseXml.class);

		// 返回结果保存
		Map<String, Object> payParamMap = new HashMap<>();
		payParamMap.put("UNIQUE_CODE", paymentUC);

		String payStatus = Const.ORDER_PAY_STATUS.WaitToPay;
		String payMessage = null;
		if ("SUCCESS".equals(response.return_code)) {
			log.info("invoke success");
			if ("SUCCESS".equals(response.result_code)) {
				log.info("payment success");
				payStatus = Const.ORDER_PAY_STATUS.Success;
				payMessage = "success";
				payParamMap.put("NONCE_STR", response.nonce_str);
				payParamMap.put("SIGN", response.sign);
				payParamMap.put("PREPAY_ID", response.prepay_id);
				payParamMap.put("CODE_URL", response.code_url);
				payParamMap.put("DEVICE_INFO", response.device_info);
				payParamMap.put("TRADE_TYPE", response.trade_type);

				payParamMap.put("OUT_TRADE_NO", tradeNo);
				payParamMap.put("TOTAL_FEE", totalAmtCent);
				payParamMap.put("TIME_START", orderApplyCal);
				payParamMap.put("TIME_EXPIRE", orderExpireCal);

			} else {
				payStatus = Const.ORDER_PAY_STATUS.Fail;
				payMessage = response.err_code_des + "<" + response.err_code + ">";
			}
		} else {
			// 配置型的错误
			payStatus = Const.ORDER_PAY_STATUS.Fail;
			payMessage = response.return_msg;
		}
		log.info("unifiedOrder end ====================>");

		payParamMap.put("PAYSTATUS", payStatus);
		payParamMap.put("PAYSTATUS_MSG", payMessage);

		dataAccessService.insertSingle("ES_PAYMENT", payParamMap);

		Map<String, Object> updateParamMap = new HashMap<>();
		updateParamMap.put("UNIQUE_CODE", orderUC);
		updateParamMap.put("PAYMENT_UC", paymentUC);

		dataAccessService.update("ES_BUSS024", updateParamMap);
	}
}
