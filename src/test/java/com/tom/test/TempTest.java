package com.tom.test;

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.junit.Ignore;
import org.junit.Test;

import com.tom.dengshaobing.common.DefaultSetting;

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
		Content content = Request.Get("http://easybuy.jd.com/address/getProvinces.action").execute().returnContent();
		String contentStr = content.asString(DefaultSetting.CHARSET);
		System.err.println(contentStr);
	}
	

}
