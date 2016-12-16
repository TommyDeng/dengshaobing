package com.tom.dengshaobing.common.bo.wmp.xml.weixinpayment;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年12月16日 下午3:17:34
 *
 */

@XmlRootElement(name = "xml")
public class ApplyOrderRequestXml {

	@XmlElement
	public String appid;
	@XmlElement
	public String mch_id;
	@XmlElement
	public String device_info;
	@XmlElement
	public String nonce_str;
	@XmlElement
	public String sign;
	@XmlElement
	public String sign_type;
	@XmlElement
	public String body;
	@XmlElement
	public String detail;
	@XmlElement
	public String attach;
	@XmlElement
	public String out_trade_no;
	@XmlElement
	public String fee_type;
	@XmlElement
	public String total_fee;
	@XmlElement
	public String spbill_create_ip;
	@XmlElement
	public String time_start;
	@XmlElement
	public String time_expire;
	@XmlElement
	public String goods_tag;
	@XmlElement
	public String notify_url;
	@XmlElement
	public String trade_type;
	@XmlElement
	public String product_id;
	@XmlElement
	public String limit_pay;
	@XmlElement
	public String openid;

}
