package com.tom.dengshaobing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import com.tom.dengshaobing.common.DefaultSetting;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月13日 下午7:53:58
 *
 */
@Configuration
@ComponentScan(basePackages = "com.tom.dengshaobing")
public class WebConfig extends WebMvcConfigurationSupport {
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	/**
	 * thymeleaf templateResolver
	 * 
	 * @return
	 */
	@Bean
	TemplateResolver templateResolver() {
		TemplateResolver templateResolver = new ServletContextTemplateResolver();
		// TemplateResolver templateResolver = new
		// ClassLoaderTemplateResolver();
		templateResolver.setPrefix("/WEB-INF/classes/thmlfpage/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setCharacterEncoding(DefaultSetting.CHARSET.name());
		return templateResolver;
	}

	/**
	 * thymeleaf templateEngine
	 * 
	 * @return
	 */
	@Bean
	SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		return templateEngine;
	}

	/**
	 * thymeleaf thymeleafViewResolver
	 * 
	 * @return
	 */
	@Bean
	ThymeleafViewResolver thymeleafViewResolver() {
		ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
		thymeleafViewResolver.setTemplateEngine(templateEngine());
		thymeleafViewResolver.setCache(false);
		thymeleafViewResolver.setCharacterEncoding(DefaultSetting.CHARSET.name());
		// thymeleafViewResolver.setOrder(1);
		// thymeleafViewResolver.setViewNames(new String[] { "*.html", "*.xhtml"
		// });
		return thymeleafViewResolver;
	}
}