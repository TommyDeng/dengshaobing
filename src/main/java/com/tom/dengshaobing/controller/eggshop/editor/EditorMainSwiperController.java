package com.tom.dengshaobing.controller.eggshop.editor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.tom.dengshaobing.common.bo.sys.MapForm;
import com.tom.dengshaobing.controller.BaseController;
import com.tom.dengshaobing.service.DataAccessService;
import com.tom.dengshaobing.service.eggshop.EggShopBussService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月26日 上午10:19:18
 *
 */

@Slf4j
@Controller
@RequestMapping("/eggshop/editor")
public class EditorMainSwiperController extends BaseController {
	public static final String BasePath = "/eggshop/editor/";

	@Autowired
	EggShopBussService bussService;

	@Autowired
	DataAccessService dataAccessService;

	@RequestMapping("/mainSwiperList")
	public String mainSwiperList(@RequestParam(name = "visitId", required = false) String visitId,
			@RequestParam(name = "visitType", required = false) String visitType, ModelMap map, String AT)
			throws Exception {
		AT = pageInit(AT, visitId, visitType, map);

		map.put("mainSwiperList", dataAccessService.queryMapList("ES_BUSS021"));

		return BasePath + "mainSwiperList";
	}

	@RequestMapping("/mainSwiperEdit")
	public String mainSwiperEdit(@RequestParam(name = "visitId", required = false) String visitId,
			@RequestParam(name = "visitType", required = false) String visitType, ModelMap map, String AT, String rowUC)
			throws Exception {
		AT = pageInit(AT, visitId, visitType, map);
		MapForm mapForm = new MapForm();
		if (rowUC != null) {
			Map<String, Object> whereParam = new HashMap<>();
			whereParam.put("UNIQUE_CODE", UUID.fromString(rowUC));

			Map<String, Object> mainSwiper = dataAccessService.queryForMapAllColumn("ES_MAIN_SWIPER", whereParam);
			mapForm.setProperties(mainSwiper);
		}

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("CATEGORY", null);
		map.put("productList", dataAccessService.queryMapList("ES_BUSS005", paramMap));

		map.put(SxFormData, mapForm);
		map.put("rowUC", rowUC);
		return BasePath + "mainSwiperEdit";
	}

	@RequestMapping("/mainSwiperSave")
	public String mainSwiperSave(@RequestParam(name = "visitId", required = false) String visitId,
			@RequestParam(name = "visitType", required = false) String visitType, ModelMap map, String rowUC, String AT,
			@ModelAttribute MapForm mapForm) throws Exception {
		AT = pageInit(AT, visitId, visitType, map);

		// 保存文件并返回UUID
		CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) mapForm.getProperties().get("THUMBNAIL");
		if (!thumbnailFile.isEmpty()) {
			UUID storeUUID = commonService.storeUploadFile(thumbnailFile);

			// 写回UUID
			mapForm.getProperties().put("THUMBNAIL", storeUUID);
		} else {
			mapForm.getProperties().remove("THUMBNAIL");
		}

		if (StringUtils.isEmpty(rowUC)) {
			mapForm.getProperties().put("UNIQUE_CODE", UUID.randomUUID());
			dataAccessService.insertSingle("ES_MAIN_SWIPER", mapForm.getProperties());
		} else {
			Map<String, Object> whereParam = new HashMap<>();
			whereParam.put("UNIQUE_CODE", UUID.fromString(rowUC));

			dataAccessService.updateSingle("ES_MAIN_SWIPER", mapForm.getProperties(), whereParam);
		}
		return "redirect:" + BasePath + "mainSwiperList";
	}

	@RequestMapping("/mainSwiperDelete")
	public String mainSwiperDelete(@RequestParam(name = "visitId", required = false) String visitId,
			@RequestParam(name = "visitType", required = false) String visitType, ModelMap map, String rowUC, String AT)
			throws Exception {
		AT = pageInit(AT, visitId, visitType, map);
		Map<String, Object> whereParam = new HashMap<>();
		whereParam.put("UNIQUE_CODE", UUID.fromString(rowUC));
		dataAccessService.deleteSingle("ES_MAIN_SWIPER", whereParam);

		return "redirect:" + BasePath + "mainSwiperList";
	}
}
