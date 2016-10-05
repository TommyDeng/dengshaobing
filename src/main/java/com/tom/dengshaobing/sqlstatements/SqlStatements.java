package com.tom.dengshaobing.sqlstatements;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.tom.dengshaobing.common.bo.sys.SqlXmlElement;
import com.tom.dengshaobing.common.bo.sys.SqlXml;
import com.tom.utils.XMLParseUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月28日 下午2:21:18
 *
 */
@Slf4j
public class SqlStatements {

	private static Map<String, String> sqlContainer = new HashMap<String, String>();
	static {
		try {
			loadAll();
		} catch (Exception e) {
			log.error("load sql xml error:", e);
		}
	}

	public static String get(String sqlName) {
		return sqlContainer.get(sqlName).toUpperCase();
	}

	private static void loadAll() throws Exception {
		URL resourceUrl = SqlStatements.class.getResource("/sqlxmls");
		File sqlXmlFileRoot = new File(resourceUrl.toURI());

		if (sqlXmlFileRoot.exists() && sqlXmlFileRoot.isDirectory()) {
			File[] sqlXmlFiles = sqlXmlFileRoot.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					// TODO Auto-generated method stub
					if (name.startsWith("sql-") && name.endsWith(".xml"))
						return true;
					else
						return false;
				}
			});

			SqlXml sqlXmlBO = null;
			InputStream sqlXmlFileInputStream = null;
			for (File sqlXmlFile : sqlXmlFiles) {
				try {
					sqlXmlFileInputStream = new FileInputStream(sqlXmlFile);
					String sqlXmlStr = IOUtils.toString(sqlXmlFileInputStream, "UTF-8");
					sqlXmlBO = XMLParseUtils.generateJavaBean(sqlXmlStr, SqlXml.class);
					for (SqlXmlElement element : sqlXmlBO.sqlElements) {
						if (sqlContainer.containsKey(element.sqlName)) {
							throw new Exception("sql name already loaded:" + element.sqlName);
						}
						sqlContainer.put(element.sqlName, element.sqlContent);
					}
				} finally {
					IOUtils.closeQuietly(sqlXmlFileInputStream);
				}
			}
		}
	}

}
