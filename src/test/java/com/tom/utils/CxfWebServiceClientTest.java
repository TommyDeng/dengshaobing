package com.tom.utils;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import junit.framework.TestSuite;

/**
 * 
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月13日 下午3:23:59
 *
 */
@Ignore
public class CxfWebServiceClientTest extends TestSuite {
	static final String testWsdlUrl = "http://10.142.142.35:9081/iCIS/services/WebServiceServer?wsdl";

	@Test
	public void testMock() {
		Assert.assertTrue(true);
	}

	@Test
	public void testInitAndInvokeOnce() {
		System.err.println(CxfWebServiceClient.initAndInvokeOnce(testWsdlUrl, "submitData", getXml()));
	}

	@Test
	public void testInvokeReuse() throws Exception {
		CxfWebServiceClient.init(testWsdlUrl);
		System.err.println(CxfWebServiceClient.invokeReuse("submitData", getXml()));
		System.err.println(CxfWebServiceClient.invokeReuse("submitData", getXml()));
		CxfWebServiceClient.destroy();
	}

	@Test(expected = Exception.class)
	public void testInvokeReuseException() throws Exception {
		System.err.println(CxfWebServiceClient.invokeReuse("submitData", getXml()));
		Assert.fail("CxfWebServiceClient client not init for reuseable invoke, pls init first.");
	}

	private static String getXml() {
		return "<?xml version=\"1.0\" encoding=\"GBK\"?>" + "<TransData>" + "<InputData>" + "<EdorType>CT</EdorType>"
				+ "<ContNo>EDWD200205</ContNo>" + "<EndDate>2016-09-01</EndDate>" + "</InputData>" + "<BaseInfo>"
				+ "<TransDate>2016-09-01</TransDate>" + "<SysOperator>SYS</SysOperator>" + "<TransType>02</TransType>"
				+ "<TransSourceSys>1100</TransSourceSys>" + "<TransSeq>020019D160901105920825</TransSeq>"
				+ "<TransTime>10:59:20</TransTime>" + "<TransCode>020019</TransCode>" + "</BaseInfo>" + "</TransData>";
	}
}
