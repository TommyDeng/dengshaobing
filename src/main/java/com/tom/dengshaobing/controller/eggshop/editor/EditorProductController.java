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
public class EditorProductController extends BaseController {
	public static final String BasePath = "/eggshop/editor/";

	@Autowired
	EggShopBussService bussService;

	@Autowired
	DataAccessService dataAccessService;


	@RequestMapping("/productList")
	public String productList(@RequestParam(name = "visitId", required = false) String visitId,@RequestParam(name = "visitType", required = false) String visitType, ModelMap map, String AT)
			throws Exception {
		AT = pageInit(AT, visitId, visitType, map);
		
		map.put("productList", dataAccessService.queryMapList("ES_BUSS022", null));
		
		return BasePath + "productList";
	}

	@RequestMapping("/productEdit")
	public String productEdit(@RequestParam(name = "visitId", required = false) String visitId,@RequestParam(name = "visitType", required = false) String visitType, ModelMap map, String AT,
			String rowUC) throws Exception {
		AT = pageInit(AT, visitId, visitType, map);
		MapForm mapForm = new MapForm();
		if (rowUC != null) {
			Map<String, Object> product = dataAccessService.queryForOneRowAllColumn("ES_PRODUCT",
					UUID.fromString(rowUC));
			mapForm.setProperties(product);
		} 

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("CATEGORY", null);
		map.put("productList", dataAccessService.queryMapList("ES_BUSS005", paramMap));
		
		map.put("categoryList", dataAccessService.queryMapList("ES_BUSS004", null));
		
		map.put(SxFormData, mapForm);
		map.put("rowUC", rowUC);
		return BasePath + "productEdit";
	}

	@RequestMapping("/productSave")
	public String productSave(@RequestParam(name = "visitId", required = false) String visitId,@RequestParam(name = "visitType", required = false) String visitType, ModelMap map,
			String rowUC, String AT, @ModelAttribute MapForm mapForm) throws Exception {
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
			dataAccessService.insertSingle("ES_PRODUCT", mapForm.getProperties());
		} else {
			dataAccessService.updateSingle("ES_PRODUCT", mapForm.getProperties());
		}
		return "redirect:" + BasePath + "productList";
	}

	@RequestMapping("/productDelete")
	public String productDelete(@RequestParam(name = "visitId", required = false) String visitId,@RequestParam(name = "visitType", required = false) String visitType, ModelMap map,
			String rowUC, String AT) throws Exception {
		AT = pageInit(AT, visitId, visitType, map);
		dataAccessService.deleteRowById("ES_PRODUCT", UUID.fromString(rowUC));

		return "redirect:" + BasePath + "productList";
	}
	@RequestMapping("/productMediaList")
	public String productMediaList(@RequestParam(name = "visitId", required = false) String visitId,@RequestParam(name = "visitType", required = false) String visitType, ModelMap map, String AT,
			String productUC) throws Exception {
		AT = pageInit(AT, visitId, visitType, map);

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("PRODUCT_UC", productUC);
		map.put("mediaList", dataAccessService.queryMapList("ES_BUSS023", paramMap));
		
		map.put("productUC", productUC);
		
		return BasePath + "productMediaList";
	}

	@RequestMapping("/productMediaEdit")
	public String productMediaEdit(@RequestParam(name = "visitId", required = false) String visitId,@RequestParam(name = "visitType", required = false) String visitType, ModelMap map, String AT,
			String rowUC,String productUC) throws Exception {
		AT = pageInit(AT, visitId, visitType, map);
		MapForm mapForm = new MapForm();
		if (rowUC != null) {
			Map<String, Object> productMedia = dataAccessService.queryForOneRowAllColumn("ES_PRODUCT_MEDIA",
					UUID.fromString(rowUC));
			mapForm.setProperties(productMedia);
		} 

		map.put(SxFormData, mapForm);
		map.put("productUC", productUC);
		map.put("rowUC", rowUC);
		return BasePath + "productMediaEdit";
	}
	
	@RequestMapping("/productMediaSave")
	public String productMediaSave(@RequestParam(name = "visitId", required = false) String visitId,@RequestParam(name = "visitType", required = false) String visitType, ModelMap map,
			String rowUC,String productUC, String AT, @ModelAttribute MapForm mapForm) throws Exception {
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
			mapForm.getProperties().put("PRODUCT_UC", productUC);
			dataAccessService.insertSingle("ES_PRODUCT_MEDIA", mapForm.getProperties());
		} else {
			dataAccessService.updateSingle("ES_PRODUCT_MEDIA", mapForm.getProperties());
		}
		
		map.put("productUC", productUC);
		return "redirect:" + BasePath + "productMediaList";
	}

	@RequestMapping("/productMediaDelete")
	public String productMediaDelete(@RequestParam(name = "visitId", required = false) String visitId,@RequestParam(name = "visitType", required = false) String visitType, ModelMap map,
			String rowUC,String productUC, String AT) throws Exception {
		AT = pageInit(AT, visitId, visitType, map);
		dataAccessService.deleteRowById("ES_PRODUCT_MEDIA", UUID.fromString(rowUC));

		map.put("productUC", productUC);
		return "redirect:" + BasePath + "productMediaList";
	}
}
