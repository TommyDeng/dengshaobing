package com.tom.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class StoredConfigUtils {

	private static String configFileRelativePath = "/stored.properties";

	private static Properties properties = null;

	public static String getValue(String key) {
		if (properties == null)
			load();
		return properties.getProperty(key);
	}

	public static void setValue(String key, String value) {
		if (properties == null)
			load();
		properties.setProperty(key, value);
		save();
	}

	private static void load() {
		InputStream inMain = null;
		try {
			inMain = StoredConfigUtils.class.getResourceAsStream(configFileRelativePath);
			properties = new Properties();
			properties.load(inMain);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inMain != null) {
					inMain.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static void save() {
		OutputStream outMain = null;
		try {
			outMain = new FileOutputStream(configFileRelativePath);
			properties.store(outMain, null);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (outMain != null) {
					outMain.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
