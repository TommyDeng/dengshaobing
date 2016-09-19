package com.tom.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;

import junit.framework.TestSuite;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月19日 上午10:42:28
 *
 */

public class HttpClientUtilsTest extends TestSuite {

	public static String doGetOnce(String url) throws ClientProtocolException, IOException {
		Content content = Request.Get(url).execute().returnContent();
		return content.asString();
	}

	public static String doGetOnce(URI uri) throws ClientProtocolException, IOException {
		Content content = Request.Get(uri).execute().returnContent();
		return content.asString();
	}
	
	public static void doPostOnce() throws ClientProtocolException, IOException {
		Request.Post("http://targethost/login")
				.bodyForm(Form.form().add("username", "vip").add("password", "secret").build()).execute()
				.returnContent();
	}
}
