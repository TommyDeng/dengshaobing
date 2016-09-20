package com.tom.dengshaobing.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.tom.utils.HttpClientUtils;
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

	String accessToken;
	AccessTokenStatus accessTokenStatus = AccessTokenStatus.NOT_INIT;

	@Override
	public boolean checkSignature(String signature, String token, String timestamp, String nonce) {
		if (signature == null)
			return false;
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
		URI uri = new URIBuilder().setScheme("https").setHost("api.weixin.qq.com").setPath("/cgi-bin/token")
				.setParameter("grant_type", "client_credential")
				.setParameter("appid", env.getProperty("WeixinPlatform.AppID"))
				.setParameter("secret", env.getProperty("WeixinPlatform.AppSecret")).build();
		String content = HttpClientUtils.doGetOnce(uri);
		accessToken = JsonParseUtils.getStringValueByFieldName(content, "access_token");
	}

	@Override
	public List<String> getIPList() throws Exception {
		URI uri = new URIBuilder().setScheme("https").setHost("api.weixin.qq.com").setPath("/cgi-bin/getcallbackip")
				.setParameter("access_token", getAccessToken()).build();
		String content = HttpClientUtils.doGetOnce(uri);
		return JsonParseUtils.getListValueByFieldName(content, "ip_list");
	}

	@Override
	public void createMenu(String menuJsonStr) throws Exception {
		URI uri = new URIBuilder().setScheme("https").setHost("api.weixin.qq.com").setPath("/cgi-bin/menu/create")
				.setParameter("access_token", getAccessToken()).build();
		HttpClientUtils.doGetOnce(uri);
	}
}
