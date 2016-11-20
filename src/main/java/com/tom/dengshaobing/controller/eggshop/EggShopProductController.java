package com.tom.dengshaobing.controller.eggshop;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.tom.dengshaobing.common.bo.sys.MapForm;
import com.tom.dengshaobing.common.bo.sys.TableMeta;
import com.tom.dengshaobing.controller.BaseController;
import com.tom.dengshaobing.service.CommonService;
import com.tom.dengshaobing.service.eggshop.EggShopBussService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月26日 上午10:19:18
 *
 */

@Slf4j
@Controller
@RequestMapping("/eggshop/product")
public class EggShopProductController extends BaseController {
	public static final String BasePath = "/eggshop/product/";

	@Autowired
	EggShopBussService bussService;

	MapForm mapForm = new MapForm();

	public MapForm getMapForm() {
		return mapForm;
	}

	public void setMapForm(MapForm mapForm) {
		this.mapForm = mapForm;
	}

	@RequestMapping("/list")
	public String list(@RequestParam(name = "openid", required = false) String openid, ModelMap map, String AT)
			throws Exception {

		if (StringUtils.isBlank(AT)) {
			AT = this.getAppToken(openid, "", commonService);
		}
		map.put(PxAT, AT);

		TableMeta tableMeta = bussService.listAllProduct();
		tableMeta.title = "Product";
		map.put(SxTableMeta, tableMeta);

		return BasePath + "list";
	}

	@RequestMapping("/edit")
	public String edit(HttpServletRequest request, HttpServletResponse response, ModelMap map, String rowUC, String AT)
			throws Exception {
		map.put(PxAT, AT);

		if (rowUC != null) {
			Map<String, Object> product = bussService.queryProduct(UUID.fromString(rowUC), AT);
			mapForm.setProperties(product);

		} else {
			mapForm = new MapForm();
		}
		map.put(SxFormData, mapForm);
		map.put(PxRowUC, rowUC);

		return BasePath + "edit";
	}

	@RequestMapping("/save")
	public String save(@ModelAttribute MapForm mapForm, ModelMap map, String rowUC, String AT) throws Exception {
		map.put(PxAT, AT);

		// 保存文件并返回UUID
		CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) mapForm.getProperties().get("THUMBNAIL");
		if (!thumbnailFile.isEmpty()) {
			UUID storeUUID = commonService.storeUploadFile(thumbnailFile);

			// 写回UUID
			mapForm.getProperties().put("THUMBNAIL", storeUUID);
		} else {
			mapForm.getProperties().remove("THUMBNAIL");
		}

		if (StringUtils.isBlank(rowUC)) {
			bussService.addProduct(mapForm.getProperties(), AT);
		} else {
			bussService.updateProduct(mapForm.getProperties(), AT);
		}
		return "redirect:list";
	}

	@RequestMapping("/delete")
	public String delete(@ModelAttribute MapForm mapForm, ModelMap map, String rowUC, String AT) throws Exception {
		map.put(PxAT, AT);

		bussService.deleteProduct(UUID.fromString(rowUC), AT);
		return "redirect:list";
	}

}
