package com.tom.dengshaobing.service;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.tom.dengshaobing.common.DefaultSetting;
import com.tom.dengshaobing.common.bo.wmp.Message;
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
	WmpBussService wmpBussService;

	@Autowired
	HttpProcessSerice httpProcessSerice;

	String accessToken;
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
	public String getAccessToken() throws Exception {
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
		accessToken = JsonParseUtils.getStringValueByFieldName(content, "access_token");
		log.info("AccessToken fetched =======================>");
		log.info(this.accessToken);
	}

	@Override
	public List<String> getIPList() throws Exception {
		URI uri = new URIBuilder("https://api.weixin.qq.com/cgi-bin/getcallbackip")
				.setParameter("access_token", getAccessToken()).build();
		String content = httpProcessSerice.httpGet(uri);
		return JsonParseUtils.getListValueByFieldName(content, "ip_list");
	}

	@Override
	public void createMenu(String menuJsonStr) throws Exception {
		URI uri = new URIBuilder("https://api.weixin.qq.com/cgi-bin/menu/create").setCharset(DefaultSetting.CHARSET)
				.setParameter("access_token", getAccessToken()).build();
		httpProcessSerice.httpPost(uri, menuJsonStr);
	}

	@Override
	public void deleteMenu() throws Exception {
		URI uri = new URIBuilder("https://api.weixin.qq.com/cgi-bin/menu/delete")
				.setParameter("access_token", getAccessToken()).build();
		httpProcessSerice.httpGet(uri);
	}

	@Override
	public Message dispatch(Message message) {
		if (message == null) {
			return null;
		}
		String eventKey = message.EventKey;
		Message returnMessage = null;
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
			break;
		}
		return returnMessage;
	}

}
