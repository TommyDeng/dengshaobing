package com.tom.dengshaobing.common.bo.wmp.xml.weixinpayment;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
import lombok.ToString;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年12月16日 下午3:17:34
 *
 */
@ToString
@XmlRootElement(name = "xml")
public class NotifyRequestXml {

	@XmlElement
	public String return_code;

	@XmlElement
	public String return_msg;

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
	public String result_code;
	@XmlElement
	public String err_code;
	@XmlElement
	public String err_code_des;
	@XmlElement
	public String openid;
	@XmlElement
	public String is_subscribe;
	@XmlElement
	public String trade_type;
	@XmlElement
	public String bank_type;
	@XmlElement
	public String total_fee;
	@XmlElement
	public String settlement_total_fee;
	@XmlElement
	public String fee_type;
	@XmlElement
	public String cash_fee;
	@XmlElement
	public String cash_fee_type;
	@XmlElement
	public String coupon_fee;
	@XmlElement
	public String coupon_count;
	@XmlElement
	public String transaction_id;
	@XmlElement
	public String out_trade_no;
	@XmlElement
	public String attach;
	@XmlElement
	public String time_end;

}
