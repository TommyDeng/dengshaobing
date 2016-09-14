package com.tom.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月13日 下午4:25:46
 *
 */
@Slf4j
public class ProjectConfigUtil {

	private static Properties projectProperties = null;

	public static String getValue(String key) {
		return projectProperties.getProperty(key);
	}

	static {
		String configFileRelativePath = "/system.properties";
		InputStream inputStream = ProjectConfigUtil.class.getResourceAsStream(configFileRelativePath);
		projectProperties = new Properties();
		try {
			projectProperties.load(inputStream);
		} catch (Exception e) {
			log.error("Properties load error! =>" + configFileRelativePath, e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					log.error("InputStream close error", e);
				}
			}
		}
	}
}
