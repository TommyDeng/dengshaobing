package com.tom.dengshaobing.controller;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tom.dengshaobing.common.Const.VISIT_TYPE;
import com.tom.dengshaobing.common.bo.wmp.json.Oauth2AccessToken;
import com.tom.dengshaobing.common.bo.wmp.json.Oauth2UserInfo;
import com.tom.dengshaobing.common.bo.wmp.type.Oauth2Scope;
import com.tom.dengshaobing.common.bo.wmp.xml.MessageXml;
import com.tom.dengshaobing.service.CommonService;
import com.tom.dengshaobing.service.WexinMessagePlatformService;

import lombok.extern.slf4j.Slf4j;

/**
 * 基础平台请求处理
 * 
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月14日 上午11:29:38
 *
 */

@Slf4j
@Controller
public class WexinMessagePlatformController {

	@Autowired
	WexinMessagePlatformService wexinMessagePlatformService;
	@Autowired
	protected CommonService commonService;

	/**
	 * 接收微信平台发送的服务器验证绑定请求,验证sha1 返回echostr参数
	 * 
	 * @param signature
	 * @param echostr
	 * @param timestamp
	 * @param nonce
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/restfull/wmp/access", method = RequestMethod.GET)
	public String wexinAccess(@RequestParam(name = "signature", required = false) String signature,
			@RequestParam(name = "echostr", required = false) String echostr,
			@RequestParam(name = "timestamp", required = false) String timestamp,
			@RequestParam(name = "nonce", required = false) String nonce) throws Exception {
		// 验证
		if (!wexinMessagePlatformService.checkSignature(signature, timestamp, nonce))
			return null;
		log.info("/restfull/wmp/access validate success =======================>");
		log.info(echostr);
		// GET请求均判定为服务器绑定认证
		return echostr;

	}

	/**
	 * 接收微信推送的用户点击动作
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param message
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/restfull/wmp/access", method = RequestMethod.POST)
	public MessageXml wexinMessageAccess(@RequestParam(value = "signature", required = false) String signature,
			@RequestParam(value = "timestamp", required = false) String timestamp,
			@RequestParam(value = "nonce", required = false) String nonce, @RequestBody MessageXml message)
			throws Exception {
		// 验证
		if (!wexinMessagePlatformService.checkSignature(signature, timestamp, nonce))
			return null;

		// POST请求均判定为消息处理
		// log.info("/restfull/wmp/access body =======================>");
		// log.info(XMLParseUtils.generateXmlString(message));

		MessageXml returnMessage = wexinMessagePlatformService.dispatch(message);

		// log.info("/restfull/wmp/access return =======================>");
		// log.info(XMLParseUtils.generateXmlString(returnMessage));
		return returnMessage;
	}

	/**
	 * 使用前先设置OAuth2.0网页授权的授权回调页面域名如：www.xxx.com
	 * 
	 * 处理REDIRECT_URL绑定的请求
	 * 
	 * @param code
	 * @param state
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/restfull/wmp/authorize", method = RequestMethod.GET)
	public String wexinAuthorize(@RequestParam(name = "code", required = false) String code,
			@RequestParam(name = "state", required = false) String state) throws Exception {

		log.info("/restfull/wmp/access wexinAuthorize revoked =======================>");

		Oauth2AccessToken oauth2AccessToken = wexinMessagePlatformService.getOauth2AccessToken(code);

		commonService.logVisit(oauth2AccessToken.openid, "weixin");

		Oauth2UserInfo userInfo = null;
		// 当scope为snsapi_userinfo，需要继续拉取用户信息
		if (Oauth2Scope.snsapi_userinfo.equals(oauth2AccessToken.scope)) {
			userInfo = wexinMessagePlatformService.getOauth2UserInfo(oauth2AccessToken);
		}

		// 保存
		wexinMessagePlatformService.storeOauth2UserInfo(oauth2AccessToken, userInfo);
		String redirectUri = contractRedirectUriByOauth2AccessToken(state, oauth2AccessToken, userInfo);
		log.info("/restfull/wmp/access wexinAuthorize redirect =======================>" + redirectUri);
		// redirect只能get,无法post
		return "redirect:" + redirectUri;
	}

	private String contractRedirectUriByOauth2AccessToken(String baseUri, Oauth2AccessToken accessToken,
			Oauth2UserInfo userInfo) throws Exception {
		URIBuilder redirectURIBuilder = new URIBuilder(baseUri);
		// 微信用户以openid为visitId标示
		redirectURIBuilder.setParameter("visitId", accessToken.openid);
		redirectURIBuilder.setParameter("visitType", VISIT_TYPE.Weixin);

		return redirectURIBuilder.build().toString();
	}

}