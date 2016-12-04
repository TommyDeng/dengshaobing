package com.tom.dengshaobing.common;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月29日 下午1:24:35
 *
 */

public class Const {
	public static final class USER_TYPE {
		public static final String Weixin = "1";
		public static final String WebPage = "2";
	};

	public static final class USER_STATUS {
		public static final String Active = "1";
		public static final String Disable = "2";
	};
	
	public static final class ORDER_STATUS {
		public static final String WaitToPay = "1";
		public static final String WaitToRecieve = "2";
		public static final String Finished = "3";
		public static final String Disable = "0";
	};

	public static final class PAYMENT_TYPE {
		public static final String Weixin = "1";
		public static final String Zhifubao = "2";
	};
}
