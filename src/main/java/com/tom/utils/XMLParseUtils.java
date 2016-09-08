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

	public static String formatXMLStr(String xmlStr) throws Exception {
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		// initialize StreamResult with File object to save to file
		StreamResult result = new StreamResult(new StringWriter());
		StreamSource source = new StreamSource(new StringReader(xmlStr));
		transformer.transform(source, result);

		return result.getWriter().toString();
	}

	public static final String UTF8_BOM = "\uFEFF";

	public static String removeUTF8BOM(String s) {
		if (s.startsWith(UTF8_BOM)) {
			s = s.substring(1);
		}
		return s;
	}
}
