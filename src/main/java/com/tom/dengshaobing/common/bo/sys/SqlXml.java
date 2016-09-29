package com.tom.dengshaobing.common.bo.sys;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "sqls")
public class SqlXml {
	
	@XmlElement(name = "sql")
	public List<SqlXmlElement> sqlElements;

}
