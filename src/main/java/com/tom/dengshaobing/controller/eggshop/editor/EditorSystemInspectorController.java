package com.tom.dengshaobing.controller.eggshop.editor;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tom.dengshaobing.controller.BaseController;

import lombok.extern.slf4j.Slf4j;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月26日 上午10:19:18
 *
 */

@Slf4j
@Controller
@RequestMapping("/eggshop/editor")
public class EditorSystemInspectorController extends BaseController {
	public static final String BasePath = "/eggshop/editor/";

	@Autowired
	private Environment env;

	@Autowired
	private MessageSource messageSource;

	@RequestMapping("/systemInspector")
	public String systemInspector(@RequestParam(name = "visitId", required = false) String visitId,
			@RequestParam(name = "visitType", required = false) String visitType, ModelMap map, String AT)
			throws Exception {
		AT = pageInit(AT, visitId, visitType, map);

		Map<String, Object> propertyMap = new TreeMap<>();
		for (Iterator<PropertySource<?>> it = ((AbstractEnvironment) env).getPropertySources().iterator(); it
				.hasNext();) {
			PropertySource<?> propertySource = it.next();
			if (propertySource instanceof ResourcePropertySource) {
				propertyMap.putAll(((MapPropertySource) propertySource).getSource());
			}
		}
		
		map.put("propertyMap", propertyMap);
		
		return BasePath + "systemInspector";
	}

}
