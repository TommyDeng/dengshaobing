package com.tom.test;

import java.net.URI;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import junit.framework.TestSuite;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月23日 下午12:03:41
 *
 */

// @Ignore
public class TempTest extends TestSuite {

	// @Test
	// public void temp() throws Exception {
	// }

	public static void main(String[] args) throws Exception {
		BasicCookieStore cookieStore = new BasicCookieStore();
		CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		try {

			HttpUriRequest login = RequestBuilder.post()
					.setUri(new URI(
							"https://passport.jd.com/uc/loginService?uuid=ab5c33c3-b0d9-4e0d-905a-826f26ca549b"))
					.addParameter("uuid", "ab5c33c3-b0d9-4e0d-905a-826f26ca549b").addParameter("eid", "FIWN6LXE7HZAX75CJPNZU6R3OXSINHHHGFD4QUTCVVYGOEWU2MQ53TZH6CKIGKWMKJYXJNOSFGC7MC3BKIP7XN2V6Q")
					.addParameter("fp", "43489ec25f4449d508e6d096255aae1b")
					.addParameter("_t", "_ntneniJ")
					.addParameter("loginType", "c")
					.addParameter("loginname", "拖米大哥")
					.addParameter("nloginpwd", "kWQXsGYkLUvR8TcWCFcirfp3214q2nV7ee2dAfQwjjpduj4zZJQf5AP39d5uyyqahy0c+PREsJ3w+KN0GcCGTd5UTJ/FbLVYnkCYryEnQY2bKoZBvvVNHjPltI+aIDoQqqMdpmUgGzPWK1t6Un2heFmHRl8QjV914Df2xwY4Hdg=")
					.addParameter("chkRememberMe", "")
					.addParameter("authcode", "")
					.build();
			CloseableHttpResponse response2 = httpclient.execute(login);
			try {
				HttpEntity entity = response2.getEntity();

				System.out.println("Login form get: " + response2.getStatusLine());
				System.out.println(EntityUtils.toString(entity));
				EntityUtils.consume(entity);

				System.out.println("Post logon cookies:");
				List<Cookie> cookies = cookieStore.getCookies();
				if (cookies.isEmpty()) {
					System.out.println("None");
				} else {
					for (int i = 0; i < cookies.size(); i++) {
						System.out.println("- " + cookies.get(i).toString());
					}
				}
			} finally {
				response2.close();
			}

			HttpGet httpget = new HttpGet("https://easybuy.jd.com/address/getProvinces.action");
			CloseableHttpResponse response1 = httpclient.execute(httpget);
			try {
				HttpEntity entity = response1.getEntity();

				System.out.println("Login form get: " + response1.getStatusLine());
				System.out.println(EntityUtils.toString(entity));
				EntityUtils.consume(entity);

				System.out.println("Initial set of cookies:");
				List<Cookie> cookies = cookieStore.getCookies();
				if (cookies.isEmpty()) {
					System.out.println("None");
				} else {
					for (int i = 0; i < cookies.size(); i++) {
						System.out.println("- " + cookies.get(i).toString());
					}
				}
			} finally {
				response1.close();
			}

		} finally {
			httpclient.close();
		}
	}
}
