package com.tom.dengshaobing.config;

import javax.servlet.Filter;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.tom.dengshaobing.common.DefaultSetting;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月14日 上午11:50:22
 *
 */
public class MyWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// return new Class[] { SecurityConfiguration.class, AppConfig.class,
		// JPAConfiguration.class };
		return new Class[] { AppConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding(DefaultSetting.CHARSET.name());
		return new Filter[] { encodingFilter };
	}
}
