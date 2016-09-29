package com.tom.dengshaobing.common.bo.sys;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class SqlXmlElement {

	@XmlValue
	public String sqlContent;

	@XmlAttribute(name = "name")
	public String sqlName;
}
