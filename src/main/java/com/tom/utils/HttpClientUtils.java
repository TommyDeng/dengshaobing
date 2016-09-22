package com.tom.utils;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.SerializableEntity;
import org.apache.http.entity.StringEntity;

import com.tom.dengshaobing.common.DefaultSetting;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月19日 上午10:42:28
 *
 */

public class HttpClientUtils {

	public static String doGetOnce(String url) throws Exception {
		Content content = Request.Get(url).execute().returnContent();
		return content.asString();
	}

	public static String doGetOnce(URI uri) throws Exception {
		Content content = Request.Get(uri).execute().returnContent();
		return content.asString();
	}

	public static String doPostOnce(URI uri, Form form) throws ClientProtocolException, IOException {
		Content content = Request.Post(uri).bodyForm(form.build()).execute().returnContent();
		return content.asString();
	}

	public static String doPostOnce(URI uri, Serializable ser) throws ClientProtocolException, IOException {
		Content content = Request.Post(uri).body(new SerializableEntity(ser)).execute().returnContent();
		return content.asString();
	}

	public static String doPostOnce(URI uri, String entityStr) throws ClientProtocolException, IOException {
		Content content = Request.Post(uri).body(new StringEntity(entityStr, DefaultSetting.CHARSET)).execute()
				.returnContent();
		return content.asString();
	}
}
