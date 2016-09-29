package com.tom.dengshaobing.service;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.tom.dengshaobing.common.DefaultSetting;
import com.tom.dengshaobing.common.bo.wmp.json.AccessToken;
import com.tom.dengshaobing.common.bo.wmp.json.Errorable;
import com.tom.dengshaobing.common.bo.wmp.json.Oauth2AccessToken;
import com.tom.dengshaobing.common.bo.wmp.json.Oauth2UserInfo;
import com.tom.dengshaobing.common.bo.wmp.xml.MessageXml;
import com.tom.utils.JsonParseUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月19日 上午10:35:02
 *
 */
@Slf4j
@Service
public class WexinMessagePlatformServiceImpl implements WexinMessagePlatformService {

	@Autowired
	private Environment env;

	@Autowired
	CommonService commonService;

	@Autowired
	WmpEventService wmpBussService;

	@Autowired
	HttpProcessSerice httpProcessSerice;

	@Override
	public MessageXml dispatch(MessageXml message) {
		if (message == null) {
			return null;
		}
		String eventKey = message.EventKey == null ? "" : message.EventKey;

		MessageXml returnMessage = null;
		switch (eventKey) {

		case "rselfmenu_1_1":
			returnMessage = wmpBussService.processMenu_1_1(message);
			break;
		case "rselfmenu_1_2":
			returnMessage = wmpBussService.processMenu_1_2(message);
			break;
		case "rselfmenu_1_3":
			returnMessage = wmpBussService.processMenu_1_3(message);
			break;
		case "rselfmenu_1_4":
			returnMessage = wmpBussService.processMenu_1_4(message);
			break;
		case "rselfmenu_1_5":
			returnMessage = wmpBussService.processMenu_1_5(message);
			break;

		case "rselfmenu_2_1":
			returnMessage = wmpBussService.processMenu_2_1(message);
			break;
		case "rselfmenu_2_2":
			returnMessage = wmpBussService.processMenu_2_2(message);
			break;
		case "rselfmenu_2_3":
			returnMessage = wmpBussService.processMenu_2_3(message);
			break;
		case "rselfmenu_2_4":
			returnMessage = wmpBussService.processMenu_2_4(message);
			break;
		case "rselfmenu_2_5":
			returnMessage = wmpBussService.processMenu_2_5(message);
			break;

		case "rselfmenu_3_1":
			returnMessage = wmpBussService.processMenu_3_1(message);
			break;
		case "rselfmenu_3_2":
			returnMessage = wmpBussService.processMenu_3_2(message);
			break;
		case "rselfmenu_3_3":
			returnMessage = wmpBussService.processMenu_3_3(message);
			break;
		case "rselfmenu_3_4":
			returnMessage = wmpBussService.processMenu_3_4(message);
			break;
		case "rselfmenu_3_5":
			returnMessage = wmpBussService.processMenu_3_5(message);
			break;
		default:
			returnMessage = wmpBussService.processInput(message);
			break;
		}
		return returnMessage;
	}

	AccessToken accessToken;
	AccessTokenStatus accessTokenStatus = AccessTokenStatus.NOT_INIT;

	@Override
	public void setAccessTokenStatus(AccessTokenStatus status) {
		this.accessTokenStatus = status;
	}

	@Override
	public AccessTokenStatus getAccessTokenStatus() {
		return this.accessTokenStatus;
	}

	@Override
	public boolean checkSignature(String signature, String timestamp, String nonce) {
		if (signature == null)
			return false;
		String token = env.getProperty("WeixinPlatform.Token");
		String[] sArray = { token, timestamp, nonce };
		Arrays.sort(sArray);
		String sha1Str = DigestUtils.sha1Hex(StringUtils.join(sArray));
		return signature.equals(sha1Str);
	}

	@Override
	public AccessToken getAccessToken() throws Exception {
		if (accessToken == null) {
			fetchAccessToken();
		}
		return accessToken;
	}

	@Override
	public void fetchAccessToken() throws Exception {
		URI uri = new URIBuilder("https://api.weixin.qq.com/cgi-bin/token")
				.setParameter("grant_type", "client_credential")
				.setParameter("appid", env.getProperty("WeixinPlatform.AppID"))
				.setParameter("secret", env.getProperty("WeixinPlatform.AppSecret")).build();
		String content = httpProcessSerice.httpGet(uri);
		AccessToken result = JsonParseUtils.generateJavaBean(content, AccessToken.class);

		logIfErrorOccurred(uri, result);

		accessToken = result;
		log.info("AccessToken fetched =======================>");
		log.info(this.accessToken.toString());
	}

	@Override
	public Oauth2AccessToken getOauth2AccessToken(String code) throws Exception {
		URI uri = new URIBuilder("https://api.weixin.qq.com/sns/oauth2/access_token")
				.setParameter("appid", env.getProperty("WeixinPlatform.AppID"))
				.setParameter("secret", env.getProperty("WeixinPlatform.AppSecret")).setParameter("code", code)
				.setParameter("grant_type", "authorization_code").build();
		String content = httpProcessSerice.httpGet(uri);
		Oauth2AccessToken result = JsonParseUtils.generateJavaBean(content, Oauth2AccessToken.class);

		logIfErrorOccurred(uri, result);

		log.info("getOauth2AccessToken =======================>");
		log.info(result.toString());

		return result;
	}

	@Override
	public Oauth2UserInfo getOauth2UserInfo(Oauth2AccessToken oauth2AccessToken) throws Exception {
		// 检验授权凭证（access_token）是否有效
		if (!checkOauth2AccessToken(oauth2AccessToken.access_token, oauth2AccessToken.openid)) {
			oauth2AccessToken = refreshOauth2AccessToken(oauth2AccessToken.refresh_token);
		}

		URI uri = new URIBuilder("https://api.weixin.qq.com/sns/userinfo")
				.setParameter("access_token", oauth2AccessToken.access_token)
				.setParameter("openid", oauth2AccessToken.openid).setParameter("lang", "zh_CN").build();
		String content = httpProcessSerice.httpGet(uri);
		Oauth2UserInfo result = JsonParseUtils.generateJavaBean(content, Oauth2UserInfo.class);
		logIfErrorOccurred(uri, result);

		log.info("getOauth2UserInfo =======================>");
		log.info(result.toString());

		return result;
	}

	@Override
	public boolean checkOauth2AccessToken(String accessToken, String openid) throws Exception {
		URI uri = new URIBuilder("https://api.weixin.qq.com/sns/auth").setParameter("access_token", accessToken)
				.setParameter("openid", openid).build();
		String content = httpProcessSerice.httpGet(uri);
		Errorable result = JsonParseUtils.generateJavaBean(content, Errorable.class);

		log.info("checkOauth2AccessToken =======================>");
		log.info(result.toString());

		return result.errcode == 0;
	}

	@Override
	public Oauth2AccessToken refreshOauth2AccessToken(String refreshToken) throws Exception {
		URI uri = new URIBuilder("https://api.weixin.qq.com/sns/oauth2/refresh_token")
				.setParameter("appid", env.getProperty("WeixinPlatform.AppID"))
				.setParameter("grant_type", "refresh_token").setParameter("refresh_token", refreshToken).build();
		String content = httpProcessSerice.httpGet(uri);
		Oauth2AccessToken result = JsonParseUtils.generateJavaBean(content, Oauth2AccessToken.class);
		logIfErrorOccurred(uri, result);

		log.info("refreshOauth2AccessToken =======================>");
		log.info(result.toString());

		return result;
	}

	@Override
	public List<String> getIPList() throws Exception {
		URI uri = new URIBuilder("https://api.weixin.qq.com/cgi-bin/getcallbackip")
				.setParameter("access_token", getAccessToken().access_token).build();
		String content = httpProcessSerice.httpGet(uri);
		return JsonParseUtils.getListValueByFieldName(content, "ip_list");
	}

	@Override
	public void createMenu(String menuJsonStr) throws Exception {
		URI uri = new URIBuilder("https://api.weixin.qq.com/cgi-bin/menu/create").setCharset(DefaultSetting.CHARSET)
				.setParameter("access_token", getAccessToken().access_token).build();
		String content = httpProcessSerice.httpPost(uri, menuJsonStr);
		Errorable result = JsonParseUtils.generateJavaBean(content, Errorable.class);
		logIfErrorOccurred(uri, result);
	}

	@Override
	public void deleteMenu() throws Exception {
		URI uri = new URIBuilder("https://api.weixin.qq.com/cgi-bin/menu/delete")
				.setParameter("access_token", getAccessToken().access_token).build();
		String content = httpProcessSerice.httpGet(uri);
		Errorable result = JsonParseUtils.generateJavaBean(content, Errorable.class);
		logIfErrorOccurred(uri, result);
	}

	private void logIfErrorOccurred(URI uri, Errorable errorable) throws Exception {
		if (errorable.errcode != null && errorable.errcode != 0) {
			commonService.logErrorable(uri.toString(), errorable);
			throw new Exception(uri.toString() + IOUtils.LINE_SEPARATOR + errorable);
		}
	}

}
