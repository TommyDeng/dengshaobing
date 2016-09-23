package com.tom.test;

import java.io.FileInputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.StringEntity;
import org.junit.Test;

import com.tom.dengshaobing.common.DefaultSetting;
import com.tom.utils.JsonParseUtils;

import junit.framework.TestSuite;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月23日 下午12:03:41
 *
 */

public class TempTest extends TestSuite {

	@Test
	public void temp() throws Exception {
		String acessToken = "g3oSD5WaY_tavEnxb1UsVuCmRfVfNps4uJD2AatSmNuT1Vag6Vw0vWBMLqsSrMP0SpTzCqUwNL2n7NvLvGjogk1seXGZ3HIk0zTq-gYYGmgJOBo4bwlov21YX2F6TSR6GONdACASAZ";
		String entityStr = IOUtils.toString(
				new FileInputStream("C:/Users/txdeng/git/dengshaobing/src/test/resources/send.json"),
				DefaultSetting.CHARSET);
		String uriStr = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=" + acessToken;

		Map<?, ?> obj = new HashMap<String, Object>();
		obj = JsonParseUtils.generateJavaBean(entityStr, obj.getClass());

		entityStr = JsonParseUtils.generateJsonString(obj);
		System.err.println(entityStr);
		Content content = Request.Post(new URI(uriStr)).body(new StringEntity(entityStr, DefaultSetting.CHARSET))
				.execute().returnContent();
		System.err.println(content.asString());
	}
}
