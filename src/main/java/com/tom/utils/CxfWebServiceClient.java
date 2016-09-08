package com.tom.utils;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

public class CxfWebServiceClient {
	private static final JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
	private static Client client;

	public static String invoke(String remoteMethod, String args) throws Exception {
		Object[] response = client.invoke(remoteMethod, args);
		return (String) response[0];
	}

	public static void init(String wsdlUrl) {
		client = dcf.createClient(wsdlUrl);
	}

	public static void destroy() {
		if (client != null) {
			client.destroy();
		}
	}

public void invokeOnce(){
		
	}

	public void invokeReuse(){
		
	}
	
}
