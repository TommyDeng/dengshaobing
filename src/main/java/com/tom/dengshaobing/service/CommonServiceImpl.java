package com.tom.dengshaobing.service;

import java.net.URI;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tom.utils.HttpClientUtils;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月14日 上午11:31:23
 *
 */
@Service
public class CommonServiceImpl implements CommonService {

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Transactional
	public void logVisit(String visitorName) {
		String sql = "insert into LOG_VISIT(name, create_time) values(:name,:create_time)";
		Map<String, Object> sqlParamMap = new HashMap<String, Object>();
		sqlParamMap.put("name", visitorName);
		sqlParamMap.put("create_time", Calendar.getInstance());
		namedParameterJdbcTemplate.update(sql, sqlParamMap);

	}

	@Override
	public List<Map<String, Object>> listVisit() {
		return namedParameterJdbcTemplate.queryForList("SELECT * FROM LOG_VISIT", new HashMap<>());
	}

	@Override
	public String httpGet(URI uri) throws Exception {
		String content = HttpClientUtils.doGetOnce(uri);
		this.logHttpRequest(HttpGet.METHOD_NAME, uri.toString(), null, content);
		return content;
	}

	@Override
	public String httpPost(URI uri, Form form) throws Exception {
		String content = HttpClientUtils.doPostOnce(uri, form);
		this.logHttpRequest(HttpPost.METHOD_NAME, uri.toString(),
				StringUtils.join(form.build().toArray(), IOUtils.LINE_SEPARATOR), content);
		return content;
	}

	@Override
	public String httpPost(URI uri, String entityStr) throws Exception {
		String content = HttpClientUtils.doPostOnce(uri, entityStr);
		this.logHttpRequest(HttpPost.METHOD_NAME, uri.toString(), entityStr, content);
		return content;
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
