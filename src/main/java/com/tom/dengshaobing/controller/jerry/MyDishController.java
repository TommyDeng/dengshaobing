package com.tom.dengshaobing.controller.jerry;

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
import com.tom.dengshaobing.controller.BaseController;
import com.tom.dengshaobing.service.jerry.MyDishService;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年11月15日 上午11:09:52
 *
 */
@Controller
@RequestMapping("/jerry/dish")
public class MyDishController extends BaseController {
	public static final String BasePath = "/jerry/dish/";

	@Autowired
	MyDishService myDishService;

	int recommendCount = 5;

	@RequestMapping("/make_diet")
	public String makeDiet(@RequestParam(name = "visitId", required = false) String visitId,
			@RequestParam(name = "visitType", required = false) String visitType, ModelMap map, String AT)
			throws Exception {
		AT = pageInit(AT, visitId, visitType, map);

		map.put("meatTableMeta", myDishService.getRecommendedMeatList(recommendCount));
		map.put("vegeTableMeta", myDishService.getRecommendedVegeList(recommendCount));

		return BasePath + "make_diet";
	}

	@RequestMapping("/changeRecommendedList")
	public String changeRecommendedList(@RequestParam(name = "visitId", required = false) String visitId,
			@RequestParam(name = "visitType", required = false) String visitType, ModelMap map, String AT, String TYPE)
			throws Exception {
		AT = pageInit(AT, visitId, visitType, map);

		map.put("meatTableMeta", myDishService.getRecommendedMeatList(recommendCount));
		map.put("vegeTableMeta", myDishService.getRecommendedVegeList(recommendCount));

		return BasePath + "make_diet";
	}

	@RequestMapping("/cookbook")
	public String cookbook(@RequestParam(name = "visitId", required = false) String visitId,
			@RequestParam(name = "visitType", required = false) String visitType, ModelMap map, String AT)
			throws Exception {

		AT = pageInit(AT, visitId, visitType, map);

		map.put(SxTableMeta, myDishService.listAllCookbook());
		return BasePath + "cookbook";
	}

	@RequestMapping("/cookbook_edit")
	public String edit(HttpServletRequest request, HttpServletResponse response, ModelMap map, String rowUC, String AT)
			throws Exception {
		map.put(PxAT, AT);

		MapForm mapForm = new MapForm();
		if (rowUC != null) {
			mapForm.setProperties(myDishService.queryCookbook(UUID.fromString(rowUC), AT));
		} 

		map.put(SxFormData, mapForm);
		map.put("rowUC", rowUC);

		return BasePath + "cookbook_edit";
	}

	@RequestMapping("/save")
	public String save(@ModelAttribute MapForm mapForm, ModelMap map, String rowUC, String AT) throws Exception {
		map.put(PxAT, AT);

		if (StringUtils.isEmpty(rowUC)) {
			myDishService.addCookbook(mapForm.getProperties(), AT);
		} else {
			myDishService.updateCookbook(mapForm.getProperties(), AT);
		}
		return "redirect:" + BasePath + "cookbook";
	}

	@RequestMapping("/delete")
	public String delete(@ModelAttribute MapForm mapForm, ModelMap map, String rowUC, String AT) throws Exception {
		map.put(PxAT, AT);

		myDishService.deleteCookbook(UUID.fromString(rowUC), AT);
		return "redirect:" + BasePath + "cookbook";
	}

	@RequestMapping("/dishes_history")
	public String dishesHistory(@RequestParam(name = "visitId", required = false) String visitId,
			@RequestParam(name = "visitType", required = false) String visitType, ModelMap map, String AT)
			throws Exception {

		AT = pageInit(AT, visitId, visitType, map);

		myDishService.listAllCookbook(AT);
		return BasePath + "dishes_history";
	}

}
