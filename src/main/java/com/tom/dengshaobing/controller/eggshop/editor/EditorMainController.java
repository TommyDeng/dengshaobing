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

	@RequestMapping("/category/list")
	public String main(@RequestParam(name = "openid", required = false) String openid, ModelMap map, String AT)
			throws Exception {
		pageInit(AT, openid, map);
		map.put("swiperList", dataAccessService.queryMapList("ES_BUSS001"));
		map.put("hotList", dataAccessService.queryMapList("ES_BUSS002"));
		map.put("recentList", dataAccessService.queryMapList("ES_BUSS003"));

		map.put("previousPage", "main");

		return BasePath + "main";
	}

	@RequestMapping("/address")
	public String address(@RequestParam(name = "openid", required = false) String openid, ModelMap map, String AT)
			throws Exception {
		pageInit(AT, openid, map);
		List<Map<String, Object>> addressList = bussService.getUserDeliveryAddressList(AT);
		map.put("addressList", addressList);

		map.put("previousPage", "myprofile");

		return BasePath + "address";
	}

	@RequestMapping("/addressEdit")
	public String addressEdit(@RequestParam(name = "openid", required = false) String openid, ModelMap map, String AT,
			String rowUC) throws Exception {
		pageInit(AT, openid, map);

		if (rowUC != null) {
			Map<String, Object> address = dataAccessService.queryForOneRowAllColumn("ES_DELIVERY_ADDRESS",
					UUID.fromString(rowUC));
			mapForm.setProperties(address);
		} else {
			mapForm = new MapForm();
		}

		map.put(SxFormData, mapForm);

		map.put("previousPage", "address");

		return BasePath + "addressEdit";
	}

	@RequestMapping("/addressSave")
	public String addressSave(@RequestParam(name = "openid", required = false) String openid, ModelMap map,
			String rowUC, String AT, @ModelAttribute MapForm mapForm) throws Exception {
		pageInit(AT, openid, map);
		if (StringUtils.isEmpty(rowUC)) {
			mapForm.getProperties().put("UNIQUE_CODE", UUID.randomUUID());
			mapForm.getProperties().put("USER_UC", commonService.getUserUCByAppToken(AT));
			dataAccessService.insertSingle("ES_DELIVERY_ADDRESS", mapForm.getProperties());
		} else {
			dataAccessService.updateSingle("ES_DELIVERY_ADDRESS", mapForm.getProperties());
		}
		return "redirect:" + BasePath + "address";
	}

	@RequestMapping("/addressDelete")
	public String addressDelete(@RequestParam(name = "openid", required = false) String openid, ModelMap map,
			String rowUC, String AT) throws Exception {
		pageInit(AT, openid, map);
		dataAccessService.deleteRowById("ES_DELIVERY_ADDRESS", UUID.fromString(rowUC));

		return "redirect:" + BasePath + "address";
	}
}
