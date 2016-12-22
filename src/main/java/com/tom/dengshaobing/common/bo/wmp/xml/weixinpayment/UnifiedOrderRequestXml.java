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
public class UnifiedOrderRequestXml {

	private String appid;

	private String mch_id;

	private String device_info;

	private String nonce_str;

	private String sign;

	private String sign_type;

	private String body;

	private String detail;

	private String attach;

	private String out_trade_no;

	private String fee_type;

	private String total_fee;

	private String spbill_create_ip;

	private String time_start;

	private String time_expire;

	private String goods_tag;

	private String notify_url;

	private String trade_type;

	private String product_id;

	private String limit_pay;

	private String openid;

	/**
	 * @return the appid
	 */
	@XmlElement
	public String getAppid() {
		return appid;
	}

	/**
	 * @return the mch_id
	 */
	@XmlElement
	public String getMch_id() {
		return mch_id;
	}

	/**
	 * @return the device_info
	 */
	@XmlElement
	public String getDevice_info() {
		return device_info;
	}

	/**
	 * @return the nonce_str
	 */
	@XmlElement
	public String getNonce_str() {
		return nonce_str;
	}

	/**
	 * @return the sign
	 */
	@XmlElement
	public String getSign() {
		return sign;
	}

	/**
	 * @return the sign_type
	 */
	@XmlElement
	public String getSign_type() {
		return sign_type;
	}

	/**
	 * @return the body
	 */
	@XmlElement
	public String getBody() {
		return body;
	}

	/**
	 * @return the detail
	 */
	@XmlElement
	public String getDetail() {
		return detail;
	}

	/**
	 * @return the attach
	 */
	@XmlElement
	public String getAttach() {
		return attach;
	}

	/**
	 * @return the out_trade_no
	 */
	@XmlElement
	public String getOut_trade_no() {
		return out_trade_no;
	}

	/**
	 * @return the fee_type
	 */
	@XmlElement
	public String getFee_type() {
		return fee_type;
	}

	/**
	 * @return the total_fee
	 */
	@XmlElement
	public String getTotal_fee() {
		return total_fee;
	}

	/**
	 * @return the spbill_create_ip
	 */
	@XmlElement
	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	/**
	 * @return the time_start
	 */
	public String getTime_start() {
		return time_start;
	}

	/**
	 * @return the time_expire
	 */
	public String getTime_expire() {
		return time_expire;
	}

	/**
	 * @return the goods_tag
	 */
	public String getGoods_tag() {
		return goods_tag;
	}

	/**
	 * @return the notify_url
	 */
	public String getNotify_url() {
		return notify_url;
	}

	/**
	 * @return the trade_type
	 */
	public String getTrade_type() {
		return trade_type;
	}

	/**
	 * @return the product_id
	 */
	public String getProduct_id() {
		return product_id;
	}

	/**
	 * @return the limit_pay
	 */
	@XmlElement
	public String getLimit_pay() {
		return limit_pay;
	}

	/**
	 * @return the openid
	 */
	@XmlElement
	public String getOpenid() {
		return openid;
	}

	/**
	 * @param appid the appid to set
	 */
	public void setAppid(String appid) {
		this.appid = appid;
	}

	/**
	 * @param mch_id the mch_id to set
	 */
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	/**
	 * @param device_info the device_info to set
	 */
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	/**
	 * @param nonce_str the nonce_str to set
	 */
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	/**
	 * @param sign the sign to set
	 */
	public void setSign(String sign) {
		this.sign = sign;
	}

	/**
	 * @param sign_type the sign_type to set
	 */
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @param detail the detail to set
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}

	/**
	 * @param attach the attach to set
	 */
	public void setAttach(String attach) {
		this.attach = attach;
	}

	/**
	 * @param out_trade_no the out_trade_no to set
	 */
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	/**
	 * @param fee_type the fee_type to set
	 */
	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	/**
	 * @param total_fee the total_fee to set
	 */
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	/**
	 * @param spbill_create_ip the spbill_create_ip to set
	 */
	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

	/**
	 * @param time_start the time_start to set
	 */
	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}

	/**
	 * @param time_expire the time_expire to set
	 */
	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}

	/**
	 * @param goods_tag the goods_tag to set
	 */
	public void setGoods_tag(String goods_tag) {
		this.goods_tag = goods_tag;
	}

	/**
	 * @param notify_url the notify_url to set
	 */
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	/**
	 * @param trade_type the trade_type to set
	 */
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	/**
	 * @param product_id the product_id to set
	 */
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	/**
	 * @param limit_pay the limit_pay to set
	 */
	public void setLimit_pay(String limit_pay) {
		this.limit_pay = limit_pay;
	}

	/**
	 * @param openid the openid to set
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}

	
	
}
