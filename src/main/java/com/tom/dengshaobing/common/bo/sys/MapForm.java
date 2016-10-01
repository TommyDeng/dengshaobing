package com.tom.dengshaobing.common.bo.sys;

import java.util.Map;

public class MapForm {

	private Map<String, Object> properties;

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	public void putAllProperties(Map<String, Object> properties) {
		if (this.properties == null) {
			this.properties = properties;
		} else {
			this.properties.putAll(properties);
		}

	}
}