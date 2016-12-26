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
public class NotifyResponseXml {
	@XmlElement
	public String return_code;

	@XmlElement
	public String return_msg;
}
