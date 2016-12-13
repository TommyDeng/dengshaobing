package com.tom.dengshaobing.common;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月29日 下午1:24:35
 *
 */

public class Const {
	public static final class VISIT_TYPE {
		public static final String Weixin = "1";//微信公众号
		public static final String WebPage = "2";//web
	};

	public static final class USER_STATUS {
		public static final String Active = "1";//启用
		public static final String Disable = "2";//禁用
	};
	
	public static final class ORDER_STATUS {
		public static final String WaitToPay = "1";//待付款
		public static final String WaitToRecieve = "2";//待收货
		public static final String Finished = "3";//已完成
		public static final String Disable = "0";//作废
	};

	public static final class PAYMENT_TYPE {
		public static final String Weixin = "1";//微信支付
		public static final String Zhifubao = "2";//支付宝
	};
}
