package com.tom.utils;

import org.junit.Test;

import junit.framework.TestSuite;
/**
 * 
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月13日 下午3:24:04
 *
 */
public class XMLParseUtilsTest extends TestSuite {
	@Test
	public void formatXMLStr() throws Exception {
		System.err.println(XMLParseUtils.formatXMLStr(getXml()));
	}

	private static String getXml() {
		return "<?xml version=\"1.0\" encoding=\"GBK\"?>" + "<TransData>" + "<InputData>" + "<EdorType>CT</EdorType>"
				+ "<ContNo>EDWD200205</ContNo>" + "<EndDate>2016-09-01</EndDate>" + "</InputData>" + "<BaseInfo>"
				+ "<TransDate>2016-09-01</TransDate>" + "<SysOperator>SYS</SysOperator>" + "<TransType>02</TransType>"
				+ "<TransSourceSys>1100</TransSourceSys>" + "<TransSeq>020019D160901105920825</TransSeq>"
				+ "<TransTime>10:59:20</TransTime>" + "<TransCode>020019</TransCode>" + "</BaseInfo>" + "</TransData>";
	}
}
