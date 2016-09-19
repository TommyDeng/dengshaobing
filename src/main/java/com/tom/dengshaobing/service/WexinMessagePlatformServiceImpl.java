package com.tom.dengshaobing.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.tom.utils.JsonParseUtils;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月19日 上午10:35:02
 *
 */

public class WexinMessagePlatformServiceImpl implements WexinMessagePlatformService {

	@Autowired
	private Environment env;

	@Override
	public boolean checkSignature(String signature, String echostr, String timestamp, String nonce) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getAccessToken() throws Exception {

		URI uri = new URIBuilder().setScheme("https").setHost("api.weixin.qq.com").setPath("/cgi-bin/token")
				.setParameter("grant_type", "client_credential")
				.setParameter("appid", env.getProperty("WeixinPlatform.AppID"))
				.setParameter("secret", env.getProperty("WeixinPlatform.AppSecret")).build();
		Content content = Request.Get(uri).execute().returnContent();
		String accessToken = JsonParseUtils.getValueByFieldName(content.asString(), "access_token");
		return accessToken;
	}

	@Override
	public List<String> getIPList() {
		// TODO Auto-generated method stub
		return null;
	}

}
