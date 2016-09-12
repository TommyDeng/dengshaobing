package com.tom.utils;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

public class CxfWebServiceClient {
	private static final JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
	private static Client client;

	//reusable flag
	private static boolean clientInitFlag = false;

	
	private static String invoke(String remoteMethod, String args) {
		Object[] response = null;
		try {
			response = client.invoke(remoteMethod, args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (String) response[0];
	}

	public static void init(String wsdlUrl) {
		client = dcf.createClient(wsdlUrl);
		clientInitFlag = true;
	}

	public static void destroy() {
		if (client != null) {
			client.destroy();
			clientInitFlag = false;
		}
	}

	public static String initAndInvokeOnce(String wsdlUrl, String remoteMethod, String args) {
		init(wsdlUrl);
		String responseStr = invoke(remoteMethod, args);
		destroy();
		return responseStr;
	}

	public static String invokeReuse(String remoteMethod, String args) throws Exception {
		if (client == null || !clientInitFlag) {
			throw new Exception("CxfWebServiceClient client not init for reuseable invoke, pls init first.");
		}
		return invoke(remoteMethod, args);
	}
}
