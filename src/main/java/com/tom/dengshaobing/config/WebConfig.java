package com.tom.dengshaobing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月13日 下午7:53:58
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.tom.dengshaobing")
public class WebConfig extends WebMvcConfigurationSupport {
	// @Override
	// public void
	// configureDefaultServletHandling(DefaultServletHandlerConfigurer
	// configurer) {
	// configurer.enable();
	// }



	/**
	 * thymeleaf templateResolver
	 * 
	 * @return
	 */
	@Bean
	ServletContextTemplateResolver templateResolver() {
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setPrefix("/template/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML5");
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
		// thymeleafViewResolver.setOrder(1);
		// thymeleafViewResolver.setViewNames(new String[] { "*.html", "*.xhtml"
		// });
		return thymeleafViewResolver;
	}
}