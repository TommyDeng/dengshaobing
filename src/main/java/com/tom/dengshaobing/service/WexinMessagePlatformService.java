package com.tom.dengshaobing.service;

import java.util.List;

/**
 * 微信公众平台服务类
 * 
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月19日 上午10:01:50
 *
 */

public interface WexinMessagePlatformService {

	/**
	 * NOT_INIT未初始化. REFETCHING 重新获取中. VALID 可用.
	 */
	enum AccessTokenStatus {
		NOT_INIT, REFETCHING, VALID
	};

	/**
	 * <pre>
	 * 验证服务器地址的有效性 
	 * 加密/校验流程如下：
	 * 1. 将token、timestamp、nonce三个参数进行字典序排序 ;
	 * 2. 将三个参数字符串拼接成一个字符串进行sha1加密 ; 
	 * 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信;
	 * </pre>
	 * 
	 * @param signature
	 * @param token
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	boolean checkSignature(String signature, String token, String timestamp, String nonce);

	/**
	 * <pre>
	 * 公众号可以使用AppID和AppSecret调用本接口来获取access_token 
	 * 接口调用请求说明 
	 * http请求方式: GET
	 * https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
	 * 参数说明
	 * 参数 是否必须 说明 : 
	 * grant_type 是 获取access_token填写client_credential 
	 * appid 是 第三方用户唯一凭证 
	 * secret 是 第三方用户唯一凭证密钥，即appsecret 
	 * 返回说明:
	 * 
	 * 正常情况下，微信会返回下述JSON数据包给公众号：
	 * {"access_token":"ACCESS_TOKEN","expires_in":7200} 
	 * 
	 * 参数 说明 access_token 获取到的凭证
	 * expires_in 凭证有效时间，单位：秒
	 * 
	 * 错误时微信会返回错误码等信息，JSON数据包示例如下（该示例为AppID无效错误）:
	 * 
	 * {"errcode":40013,"errmsg":"invalid appid"}
	 * </pre>
	 * 
	 * AppID(应用ID)wx3bcb302e3fccb280
	 * AppSecret(应用密钥)5f5518e57d7b6ff06ff084d97e2f6295
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
	 * <pre>
	 * 获取微信服务器IP地址
	如果公众号基于消息接收安全上的考虑，需要获知微信服务器的IP地址列表，以便识别出哪些消息是微信官方推送给你的，哪些消息可能是他人伪造的，可以通过该接口获得微信服务器IP地址列表。
	
	接口调用请求说明
	
	http请求方式: GET
	https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=ACCESS_TOKEN
	参数说明
	
	参数	是否必须	说明
	access_token	是	公众号的access_token
	返回说明
	
	正常情况下，微信会返回下述JSON数据包给公众号：
	
	{
	"ip_list":["127.0.0.1","127.0.0.1"]
	}
	参数	说明
	ip_list	微信服务器IP地址列表
	错误时微信会返回错误码等信息，JSON数据包示例如下（该示例为AppID无效错误）:
	
	{"errcode":40013,"errmsg":"invalid appid"}
	 * </pre>
	 * 
	 * @return
	 * @throws Exception
	 */
	List<String> getIPList() throws Exception;

}
