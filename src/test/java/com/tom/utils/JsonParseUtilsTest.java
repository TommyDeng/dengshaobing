package com.tom.utils;

import java.util.Date;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.tom.dengshaobing.common.TestBO;

import junit.framework.TestSuite;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月19日 下午12:08:16
 *
 */

@Ignore
public class JsonParseUtilsTest extends TestSuite {
	@Test
	public void generateJsonString() throws Exception {
		// Serialization
		TestBO testBO = new TestBO();
		testBO.setVarString("Tommy");
		testBO.setVarInt(123);
		testBO.setVarDate(new Date(1474262364360L));
		String result = JsonParseUtils.generateJsonString(testBO);
		Assert.assertEquals("{\"varString\":\"Tommy\",\"varInt\":123,\"varDate\":\"Sep 19, 2016 1:19:24 PM\"}", result);

	}

	@Test
	public void generateJavaBean() throws Exception {
		// Deserialization
		String jsonStr = "{\"varString\":\"Tommy\",\"varInt\":123,\"varDate\":\"Sep 19, 2016 1:19:24 PM\"}";
		TestBO testBO = JsonParseUtils.generateJavaBean(jsonStr, TestBO.class);
		Assert.assertEquals("Tommy", testBO.getVarString());
		Assert.assertEquals(123, testBO.getVarInt());
	}

	@Test
	public void getStringValueByFieldName() throws Exception {
		String jsonStr = "{\"varString\":\"Tommy\",\"varInt\":123,\"varDate\":\"Sep 19, 2016 1:19:24 PM\"}";

		String fieldValue = JsonParseUtils.getStringValueByFieldName(jsonStr, "varString");
		Assert.assertEquals("Tommy", fieldValue);
	}

}
