package com.tom.dengshaobing.controller.eggshop.editor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.tom.dengshaobing.common.Const;
import com.tom.dengshaobing.common.bo.sys.ListForm;
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
public class EditorMainController extends BaseController {
	public static final String BasePath = "/eggshop/editor/";

	@Autowired
	EggShopBussService bussService;

	@Autowired
	DataAccessService dataAccessService;

	MapForm mapForm = new MapForm();

	public MapForm getMapForm() {
		return mapForm;
	}

	public void setMapForm(MapForm mapForm) {
		this.mapForm = mapForm;
	}

	ListForm listForm = new ListForm();

	public ListForm getListForm() {
		return listForm;
	}

	public void setListForm(ListForm listForm) {
		this.listForm = listForm;
	}

	@RequestMapping("/categoryList")
	public String categoryList(@RequestParam(name = "openid", required = false) String openid, ModelMap map, String AT)
			throws Exception {
		pageInit(AT, openid, map);

		map.put("categoryList", dataAccessService.queryMapList("ES_BUSS004"));

		return BasePath + "categoryList";
	}

	@RequestMapping("/categoryEdit")
	public String categoryEdit(@RequestParam(name = "openid", required = false) String openid, ModelMap map, String AT,
			String rowUC) throws Exception {
		pageInit(AT, openid, map);

		if (rowUC != null) {
			Map<String, Object> category = dataAccessService.queryForOneRowAllColumn("ES_PRODUCT_CATEGORY",
					UUID.fromString(rowUC));
			mapForm.setProperties(category);
		} else {
			mapForm = new MapForm();
		}
		map.put(SxFormData, mapForm);
		map.put("rowUC", rowUC);
		return BasePath + "categoryEdit";
	}

	@RequestMapping("/categorySave")
	public String categorySave(@RequestParam(name = "openid", required = false) String openid, ModelMap map,
			String rowUC, String AT, @ModelAttribute MapForm mapForm) throws Exception {
		pageInit(AT, openid, map);

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
			dataAccessService.insertSingle("ES_PRODUCT_CATEGORY", mapForm.getProperties());
		} else {
			dataAccessService.updateSingle("ES_PRODUCT_CATEGORY", mapForm.getProperties());
		}
		return "redirect:" + BasePath + "categoryList";
	}

	@RequestMapping("/categoryDelete")
	public String categoryDelete(@RequestParam(name = "openid", required = false) String openid, ModelMap map,
			String rowUC, String AT) throws Exception {
		pageInit(AT, openid, map);
		dataAccessService.deleteRowById("ES_PRODUCT_CATEGORY", UUID.fromString(rowUC));

		return "redirect:" + BasePath + "categoryList";
	}
}
