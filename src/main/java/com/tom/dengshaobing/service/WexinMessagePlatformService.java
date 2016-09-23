package com.tom.dengshaobing.service;

import java.util.List;

import com.tom.dengshaobing.common.bo.wmp.xml.MessageXml;

/**
 * 微信公众平台服务类
 * 
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月19日 上午10:01:50
 *
 */

public interface WexinMessagePlatformService {
	/**
	 * 将消息分配给相应的服务类处理
	 * 
	 * @param message
	 * @return
	 */
	MessageXml dispatch(MessageXml message);

	/**
	 * NOT_INIT未初始化. REFETCHING 重新获取中. VALID 可用.
	 */
	enum AccessTokenStatus {
		NOT_INIT, REFETCHING, VALID
	};

	/**
	 * 设置当前AccessToken Status
	 * 
	 * @param status
	 */
	void setAccessTokenStatus(AccessTokenStatus status);

	/**
	 * 获取当前AccessToken Status
	 * 
	 * @param status
	 */
	AccessTokenStatus getAccessTokenStatus();

	/**
	 * 验证消息发送服务器地址的有效性
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	boolean checkSignature(String signature, String timestamp, String nonce);

	/**
	 * 获取AccessToken
	 * 
	 * @return
	 * @throws Exception
	 */
	void fetchAccessToken() throws Exception;

	/**
	 * 获得当前 access_token
	 * 
	 * @return
	 * @throws Exception
	 */
	String getAccessToken() throws Exception;

	/**
	 * 获取微信服务器IP地址
	 * 
	 * @return
	 * @throws Exception
	 */
	List<String> getIPList() throws Exception;

	/**
	 * 创建公众号菜单
	 * 
	 * @param menuJsonStr
	 * @return
	 * @throws Exception
	 */
	void createMenu(String menuJsonStr) throws Exception;

	/**
	 * 删除公众号菜单
	 * 
	 * @throws Exception
	 */
	void deleteMenu() throws Exception;

	
}
