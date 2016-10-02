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

	@Autowired
	CommonService commonService;

	@Autowired
	EggShopBussService bussService;

	MapForm mapForm = new MapForm();

	public MapForm getMapForm() {
		return mapForm;
	}

	public void setMapForm(MapForm mapForm) {
		this.mapForm = mapForm;
	}

	// product start
	@RequestMapping("/list")
	public String productList(@RequestParam(name = "openid", required = false) String openid, ModelMap map)
			throws Exception {
		UUID userUC = bussService.getUserUCByOpenid(openid);

		// all product
		TableMeta tableMeta = bussService.listAllProduct();
		tableMeta.title = "product";
		map.put(SxTableMeta, tableMeta);

		return "list";
	}

	@RequestMapping("/edit")
	public String productInit(HttpServletRequest request, HttpServletResponse response, ModelMap map, String rowUC)
			throws Exception {
		if (rowUC != null) {
			Map<String, Object> product = bussService.queryProduct(UUID.fromString(rowUC), null);
			Map<String, Object> productDetail = bussService.queryProductDetail(UUID.fromString(rowUC), null);
			mapForm.setProperties(product);
			mapForm.putAllProperties(productDetail);

		} else {
			mapForm = new MapForm();
		}
		map.put(SxFormData, mapForm);
		map.put(PxRowUC, rowUC);

		return "edit";
	}

	@RequestMapping("/save")
	public String productSave(@ModelAttribute MapForm mapForm, ModelMap map, String rowUC) throws Exception {
		UUID userUC = (UUID) mapForm.getProperties().get(PxUserUC);

		String rowUC2 = (String) mapForm.getProperties().get("UNIQUE_CODE");
		if (StringUtils.isBlank(rowUC)) {
			bussService.addProduct(mapForm.getProperties(), userUC);
		} else {
			bussService.updateProduct(mapForm.getProperties(), userUC);
		}
		return "redirect:list";
	}

	@RequestMapping("/delete")
	public String productDelete(@ModelAttribute MapForm mapForm, ModelMap map, String rowUC) throws Exception {
		bussService.deleteProduct(UUID.fromString(rowUC), null);
		return "redirect:list";
	}

}
