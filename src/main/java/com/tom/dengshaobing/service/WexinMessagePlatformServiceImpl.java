package com.tom.dengshaobing.service;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.tom.utils.JsonParseUtils;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月19日 上午10:35:02
 *
 */
@Service
public class WexinMessagePlatformServiceImpl implements WexinMessagePlatformService {

	@Autowired
	private Environment env;

	@Autowired
	CommonService commonService;

	String accessToken;
	AccessTokenStatus accessTokenStatus = AccessTokenStatus.NOT_INIT;

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
		String content = commonService.httpGet(uri);
		accessToken = JsonParseUtils.getStringValueByFieldName(content, "access_token");
	}

	@Override
	public List<String> getIPList() throws Exception {
		URI uri = new URIBuilder("https://api.weixin.qq.com/cgi-bin/getcallbackip")
				.setParameter("access_token", getAccessToken()).build();
		String content = commonService.httpGet(uri);
		return JsonParseUtils.getListValueByFieldName(content, "ip_list");
	}

	@Override
	public void createMenu(String menuJsonStr) throws Exception {
		URI uri = new URIBuilder("https://api.weixin.qq.com/cgi-bin/menu/create")
				.setParameter("access_token", getAccessToken()).build();
		Form form = Form.form().add("body", menuJsonStr);
		commonService.httpPost(uri, form);
	}
}
