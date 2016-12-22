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
public class UnifiedOrderResponseXml {

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
	public String result_code;
	@XmlElement
	public String err_code;
	@XmlElement
	public String err_code_des;
	@XmlElement
	public String trade_type;
	@XmlElement
	public String prepay_id;
	@XmlElement
	public String code_url;
}
