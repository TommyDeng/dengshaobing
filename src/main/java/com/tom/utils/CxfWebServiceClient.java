package com.tom.utils;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月13日 下午3:22:01
 *
 */
@Slf4j
public class CxfWebServiceClient {
	private static final JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
	private static Client client;

	// reusable flag
	private static boolean clientInitFlag = false;

	/**
	 * invoke
	 * 
	 * @param remoteMethod
	 * @param args
	 * @return
	 */
	private static String invoke(String remoteMethod, String args) {
		Object[] response = null;
		try {
			response = client.invoke(remoteMethod, args);
		} catch (Exception e) {
			log.error("CxfWebServiceClient invoke error", e);
		}
		return (String) response[0];
	}

	/**
	 * reusable调用前,必须调用初始化方法
	 * 
	 * @param wsdlUrl
	 */
	public static void init(String wsdlUrl) {
		client = dcf.createClient(wsdlUrl);
		clientInitFlag = true;
	}

	/**
	 * 可多次调用，不销毁client，减少开销
	 * 
	 * @param remoteMethod
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static String invokeReuse(String remoteMethod, String args) throws Exception {
		if (client == null || !clientInitFlag) {
			throw new Exception("CxfWebServiceClient client not init for reuseable invoke, pls init first.");
		}
		return invoke(remoteMethod, args);
	}

	/**
	 * reusable调用后,必须调用销毁
	 */
	public static void destroy() {
		if (client != null) {
			client.destroy();
			clientInitFlag = false;
		}
	}

	/**
	 * 单次调用wsdl，指定方法名并传入报文字符串，返回相应报文字符串
	 * 
	 * @param wsdlUrl
	 * @param remoteMethod
	 * @param args
	 * @return
	 */
	public static String initAndInvokeOnce(String wsdlUrl, String remoteMethod, String args) {
		init(wsdlUrl);
		String responseStr = invoke(remoteMethod, args);
		destroy();
		return responseStr;
	}

}
