package com.tom.dengshaobing.service;

import java.net.URI;
import java.util.List;

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
	public boolean checkSignature(String signature, String echostr, String timestamp, String nonce) {
		// TODO Auto-generated method stub
		return false;
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
				.setParameter("access_token", "client_credential").build();
		String content = HttpClientUtils.doGetOnce(uri);
		return JsonParseUtils.getListValueByFieldName(content, "ip_list");
	}
}
