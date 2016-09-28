package com.tom.test;

import java.util.HashMap;
import java.util.Map;

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
		
		
		System.err.println(DefaultSetting.CHARSET.name());
		
	}
}
