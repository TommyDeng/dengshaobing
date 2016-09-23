package com.tom.dengshaobing.service;

import java.io.Serializable;
import java.net.URI;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.SerializableEntity;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.tom.dengshaobing.common.DefaultSetting;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月14日 上午11:31:23
 *
 */
@Service
public class HttpProcessSericeImpl implements HttpProcessSerice {

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public String httpGet(URI uri) throws Exception {
		Content content = Request.Get(uri).execute().returnContent();
		String contentStr = content.asString();
		this.logHttpRequest(HttpGet.METHOD_NAME, uri.toString(), null, contentStr);
		return contentStr;
	}

	@Override
	public String httpPost(URI uri, Form form) throws Exception {
		Content content = Request.Post(uri).bodyForm(form.build()).execute().returnContent();
		String contentStr = content.asString();
		this.logHttpRequest(HttpPost.METHOD_NAME, uri.toString(),
				StringUtils.join(form.build().toArray(), IOUtils.LINE_SEPARATOR), contentStr);
		return contentStr;
	}

	@Override
	public String httpPost(URI uri, String entityStr) throws Exception {
		Content content = Request.Post(uri).body(new StringEntity(entityStr, DefaultSetting.CHARSET)).execute()
				.returnContent();
		String contentStr = content.asString();
		this.logHttpRequest(HttpPost.METHOD_NAME, uri.toString(), entityStr, contentStr);
		return contentStr;
	}

	@Override
	@SuppressWarnings(value = { "under implementing" })
	public Serializable httpPost(URI uri, Serializable ser) throws Exception {
		Content content = Request.Post(uri).body(new SerializableEntity(ser)).execute().returnContent();
		return content.asString();
	}

	private void logHttpRequest(String httpMethodName, String uri, String formStr, String responseStr) {
		String sql = "insert into log_http(method_name, uri, request, response, create_time) "
				+ "values (:method_name, :uri, :request, :response, :create_time)";
		Map<String, Object> sqlParamMap = new HashMap<String, Object>();
		sqlParamMap.put("method_name", httpMethodName);
		sqlParamMap.put("uri", uri);
		sqlParamMap.put("request", formStr);
		sqlParamMap.put("response", responseStr);
		sqlParamMap.put("create_time", Calendar.getInstance());
		namedParameterJdbcTemplate.update(sql, sqlParamMap);
	}
}
