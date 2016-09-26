package com.tom.test;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.core.env.Environment;

import com.tom.dengshaobing.common.DefaultSetting;
import com.tom.dengshaobing.common.bo.wmp.json.Oauth2Scope;
import com.tom.dengshaobing.common.bo.wmp.xml.Menu;
import com.tom.utils.JsonParseUtils;

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
		System.out.println(Oauth2Scope.snsapi_base.toString());
	}
}
