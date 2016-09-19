package com.tom.dengshaobing.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Properties;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;

import com.tom.utils.HttpClientUtils;
import com.tom.utils.JsonParseUtils;
import com.tom.utils.StoredConfigUtils;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月19日 上午10:35:02
 *
 */

public class WexinMessagePlatformServiceImpl implements WexinMessagePlatformService {

	@Autowired
	private Environment env;

	/**
	 * NOT_INIT未初始化. REFETCHING 重新获取中. VALID 可用.
	 */
	enum AccessTokenStatus {
		NOT_INIT, REFETCHING, VALID
	};

	String accessToken;
	AccessTokenStatus accessTokenStatus = AccessTokenStatus.NOT_INIT;

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

		String content = HttpClientUtils.doGetOnce(uri);
		return JsonParseUtils.getStringValueByFieldName(content, "access_token");
	}

	boolean checkAccessTokenValid() {

		return false;
	}

	@Override
	public List<String> getIPList() throws Exception {
		URI uri = new URIBuilder().setScheme("https").setHost("api.weixin.qq.com").setPath("/cgi-bin/getcallbackip")
				.setParameter("access_token", "client_credential").build();
		String content = HttpClientUtils.doGetOnce(uri);
		return JsonParseUtils.getListValueByFieldName(content, "ip_list");
	}
}
