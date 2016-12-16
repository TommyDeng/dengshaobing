package com.tom.dengshaobing.service;

import java.util.UUID;

/**
 * 微信支付服务类
 * 
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年12月16日 上午11:49:54
 *
 */
public interface WexinPaymentService {

	/**
	 * 
	 * @param orderUC
	 * @param ipAddress
	 * @param AT
	 * @throws Exception
	 */
	void applyOrder(UUID orderUC,String ipAddress, String AT) throws Exception;

	
}
