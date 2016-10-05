package com.tom.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import com.tom.dengshaobing.common.bo.wmp.json.Oauth2UserInfo;
import com.tom.utils.SqlUtils;

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
		Oauth2UserInfo userInfo = new Oauth2UserInfo();
		userInfo.city = "123";
		Map<String, Object> paramMap = new HashMap<>();
		BeanUtils.populate(userInfo, paramMap);
		System.out.println(SqlUtils.revertKeyUpcase(paramMap));
	}
}
