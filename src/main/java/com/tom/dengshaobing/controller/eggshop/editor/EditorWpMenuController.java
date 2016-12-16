package com.tom.dengshaobing.controller.eggshop.editor;

import java.io.InputStream;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.tom.dengshaobing.common.DefaultSetting;
import com.tom.dengshaobing.common.bo.sys.ListForm;
import com.tom.dengshaobing.common.bo.sys.MapForm;
import com.tom.dengshaobing.common.bo.wmp.json.Menu;
import com.tom.dengshaobing.controller.BaseController;
import com.tom.dengshaobing.service.DataAccessService;
import com.tom.dengshaobing.service.WexinMessagePlatformService;
import com.tom.dengshaobing.service.eggshop.EggShopBussService;
import com.tom.utils.JsonParseUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月26日 上午10:19:18
 *
 */

@Slf4j
@Controller
@RequestMapping("/eggshop/editor")
public class EditorWpMenuController extends BaseController {
	public static final String BasePath = "/eggshop/editor/";

	@Autowired
	WexinMessagePlatformService wexinMessagePlatformService;

	@RequestMapping("/wpMenu")
	public String wpMenu(@RequestParam(name = "visitId", required = false) String visitId,
			@RequestParam(name = "visitType", required = false) String visitType, ModelMap map, String AT)
			throws Exception {
		AT = pageInit(AT, visitId, visitType, map);

		MapForm mapForm = new MapForm();
		map.put(SxFormData, mapForm);

		return BasePath + "wpMenu";
	}

	@RequestMapping("/wpMenuReload")
	public String wpMenuReload(@RequestParam(name = "visitId", required = false) String visitId,
			@RequestParam(name = "visitType", required = false) String visitType, ModelMap map, String AT,
			@ModelAttribute MapForm mapForm) throws Exception {
		AT = pageInit(AT, visitId, visitType, map);
		CommonsMultipartFile menuFile = (CommonsMultipartFile) mapForm.getProperties().get("MENU_JSON_FILE");
		if (!menuFile.isEmpty()) {
			try {

				log.info("menu reload start =======================>");
				wexinMessagePlatformService.deleteMenu();

				InputStream inputStream = menuFile.getInputStream();
				String menuJsonStr = IOUtils.toString(inputStream, DefaultSetting.CHARSET);
				IOUtils.closeQuietly(inputStream);

				// convert to compact json string
				Menu menu = JsonParseUtils.generateJavaBean(menuJsonStr, Menu.class);
				menuJsonStr = JsonParseUtils.generateJsonString(menu);

				wexinMessagePlatformService.createMenu(menuJsonStr);

				log.info("menu reload end =======================>");
				map.put("msg", "Reload Successfull!");
			} catch (Exception e) {
				log.error("menu reload error", e);
				map.put("msg", "Reload Failed!");
			}
		}
		mapForm = new MapForm();
		map.put(SxFormData, mapForm);

		return BasePath + "wpMenu";
	}

}
