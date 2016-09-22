package com.tom.utils;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import junit.framework.TestSuite;

/**
 * 
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月13日 下午4:25:46
 *
 */
@Ignore
public class ProjectConfigUtilsTest extends TestSuite {
	@Test
	public void getValue() {
		Assert.assertEquals("dummy", ProjectConfigUtils.getValue("dummy"));
	}
}
