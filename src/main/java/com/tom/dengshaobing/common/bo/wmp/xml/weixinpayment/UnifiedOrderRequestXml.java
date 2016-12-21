package com.tom.dengshaobing.common.bo.wmp.xml.weixinpayment;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年12月16日 下午3:17:34
 *
 */

@XmlRootElement(name = "xml")
@Data
public class UnifiedOrderRequestXml {

	@XmlElement
	private String appid;
	@XmlElement
	private String mch_id;
	@XmlElement
	private String device_info;
	@XmlElement
	private String nonce_str;
	@XmlElement
	private String sign;
	@XmlElement
	private String sign_type;
	@XmlElement
	private String body;
	@XmlElement
	private String detail;
	@XmlElement
	private String attach;
	@XmlElement
	private String out_trade_no;
	@XmlElement
	private String fee_type;
	@XmlElement
	private String total_fee;
	@XmlElement
	private String spbill_create_ip;
	@XmlElement
	private String time_start;
	@XmlElement
	private String time_expire;
	@XmlElement
	private String goods_tag;
	@XmlElement
	private String notify_url;
	@XmlElement
	private String trade_type;
	@XmlElement
	private String product_id;
	@XmlElement
	private String limit_pay;
	@XmlElement
	private String openid;

}
