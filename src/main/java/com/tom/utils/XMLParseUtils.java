package com.tom.utils;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * 
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月13日 下午3:22:28
 *
 */
public class XMLParseUtils {
	/**
	 * object生成xml
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static String generateXmlString(Object obj) throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		// jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		String resultStr = null;
		StringWriter strwriter = null;
		try {
			strwriter = new StringWriter();
			jaxbMarshaller.marshal(obj, strwriter);
			strwriter.flush();
			resultStr = strwriter.toString();
		} finally {
			if (strwriter != null) {
				strwriter.close();
			}
		}
		return resultStr;
	}

	/**
	 * xml生成object
	 * 
	 * @param xmlStr
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T generateJavaBean(String xmlStr, Class<T> cls) throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(cls);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

		T returnObject = null;
		StringReader strReader = null;

		try {
			strReader = new StringReader(xmlStr);
			returnObject = (T) jaxbUnmarshaller.unmarshal(strReader);
		} finally {
			if (strReader != null) {
				strReader.close();
			}
		}
		return returnObject;
	}

	/**
	 * 格式化xml字符串，缩进当量2
	 * 
	 * @param xmlStr
	 * @return
	 * @throws Exception
	 */
	public static String formatXMLStr(String xmlStr) throws Exception {
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		// initialize StreamResult with File object to save to file
		StreamResult result = new StreamResult(new StringWriter());
		StreamSource source = new StreamSource(new StringReader(xmlStr));
		transformer.transform(source, result);

		return result.getWriter().toString();
	}

	public static final String UTF8_BOM = "\uFEFF";

	/**
	 * 除去utf-8不可见的字符
	 * 
	 * @param s
	 * @return
	 */
	public static String removeUTF8BOM(String s) {
		if (s.startsWith(UTF8_BOM)) {
			s = s.substring(1);
		}
		return s;
	}
}
