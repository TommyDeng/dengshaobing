package com.tom.utils;

import java.net.URI;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.tom.dengshaobing.common.DefaultSetting;
import com.tom.dengshaobing.common.bo.wmp.Message;

import junit.framework.TestSuite;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月19日 上午10:42:28
 *
 */
@Ignore
public class HttpClientUtilsTest extends TestSuite {
	@Test
	public void testMock() {
		Assert.assertTrue(true);
	}

	@Test
	public void testHttpPost() throws Exception {

		String request = IOUtils.toString(HttpClientUtilsTest.class.getResourceAsStream("/send.xml"),
				DefaultSetting.CHARSET);
		Message message = XMLParseUtils.generateJavaBean(request, Message.class);

		HttpClientUtils.doPostOnce(new URI("http://localhost:8080/dengshaobing/restfull/wmp/access"), message);

	}
}
