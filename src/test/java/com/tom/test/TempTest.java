package com.tom.test;

import org.junit.Test;

import com.tom.dengshaobing.common.bo.wmp.json.Oauth2AccessToken;
import com.tom.dengshaobing.common.bo.wmp.json.Oauth2Scope;

import junit.framework.TestSuite;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月23日 下午12:03:41
 *
 */

// @Ignore
public class TempTest extends TestSuite {

	@Test
	public void temp() throws Exception {
		Oauth2AccessToken Oauth2AccessToken = new Oauth2AccessToken();
		Oauth2AccessToken.access_token = "aaa";
		Oauth2AccessToken.openid = "bbb";
		Oauth2AccessToken.expires_in = 123L;
		Oauth2AccessToken.scope = Oauth2Scope.snsapi_userinfo;
		System.out.println(Oauth2AccessToken.toString());
	}
}
