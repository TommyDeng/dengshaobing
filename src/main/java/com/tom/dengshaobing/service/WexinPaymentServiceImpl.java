package com.tom.dengshaobing.service;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.tom.dengshaobing.common.Const;
import com.tom.dengshaobing.common.DefaultSetting;
import com.tom.dengshaobing.common.bo.wmp.json.AccessToken;
import com.tom.dengshaobing.common.bo.wmp.json.Errorable;
import com.tom.dengshaobing.common.bo.wmp.json.Oauth2AccessToken;
import com.tom.dengshaobing.common.bo.wmp.json.Oauth2UserInfo;
import com.tom.dengshaobing.common.bo.wmp.xml.MessageXml;
import com.tom.dengshaobing.common.bo.wmp.xml.weixinpayment.ApplyOrderRequestXml;
import com.tom.utils.DateTimeUtils;
import com.tom.utils.JsonParseUtils;
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
	public void applyOrder(UUID orderUC, String ipAddress, String AT) throws Exception {
		Map<String, Object> orderRow = dataAccessService.queryForOneRowAllColumn("ES_ORDER", orderUC);

		ApplyOrderRequestXml request = new ApplyOrderRequestXml();
		Map<String, String> paramMap = new TreeMap<>();
		// 公众账号ID
		paramMap.put("appid", env.getProperty("WeixinPlatform.AppID"));
		// 商户号
		paramMap.put("mch_id", env.getProperty("WeixinPlatform.MerchantID"));
		// 设备号
		paramMap.put("mch_id", "WEB");
		// 随机字符串
		paramMap.put("nonce_str", UUID.randomUUID().toString().replaceAll("-", ""));

		// 签名类型
		paramMap.put("sign_type", "MD5");

		// 商品描述 商家名称-销售商品类目
		paramMap.put("body", "乐果农场-产品购买");

		// 商品详情
		// paramMap.put("detail", "商品详情");

		// 附加数据
		// paramMap.put("attach", "微信公众号分店");

		// 商户订单号 当前时间戳+当前线程ID
		String tradeNo = DateTimeUtils.getCurrentFormatString("yyyyMMddHHmmssSSS")
				+ String.valueOf(Thread.currentThread().getId());
		paramMap.put("out_trade_no", tradeNo);

		// 标价币种
		paramMap.put("fee_type", "CNY");

		// 标价金额 单位为分
		BigDecimal totalAmt = (BigDecimal) orderRow.get("TOTAL_AMT");
		String totalAmtCent = totalAmt.multiply(new BigDecimal(100)).toString();
		paramMap.put("total_fee", totalAmtCent);

		// 终端IP
		paramMap.put("spbill_create_ip", ipAddress);

		Calendar orderApplyCal = Calendar.getInstance();
		// 交易起始时间 订单生成时间
		paramMap.put("time_start", DateTimeUtils.getFormatStringByDate(orderApplyCal.getTime(), "yyyyMMddHHmmss"));

		Calendar orderExpireCal = Calendar.getInstance();
		orderExpireCal.add(Calendar.DAY_OF_YEAR, 1);// 暂定为一天后失效
		// 交易结束时间 订单失效时间
		paramMap.put("time_expire", DateTimeUtils.getFormatStringByDate(orderExpireCal.getTime(), "yyyyMMddHHmmss"));

		// 商品标记
		// paramMap.put("goods_tag", "WXG");

		// 通知地址
		paramMap.put("notify_url", env.getProperty("WeixinPlatform.Payment.TestRoot")+"applyOrder");

		// 交易类型
		paramMap.put("trade_type", "JSAPI");

		// 商品ID product_id
		// 指定支付方式 limit_pay
		// 用户标识 openid
		paramMap.put("openid", AT);
		
		// 签名 sign
		paramMap.put("sign", "");?

		
		// URL地址：https://api.mch.weixin.qq.com/pay/unifiedorder
		URI uri = new URIBuilder("https://api.mch.weixin.qq.com/pay/unifiedorder").setCharset(DefaultSetting.CHARSET).build();
		httpProcessSerice.httpPost(uri, XMLParseUtils.generateXmlString(request));
	}

}
