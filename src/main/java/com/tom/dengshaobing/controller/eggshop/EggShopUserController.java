package com.tom.dengshaobing.controller.eggshop;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

@Controller
@RequestMapping("/eggshop/user")
public class EggShopUserController extends BaseController {
	public static final String BasePath = "/eggshop/user/";

	@Autowired
	EggShopBussService bussService;

	MapForm mapForm = new MapForm();

	public MapForm getMapForm() {
		return mapForm;
	}

	public void setMapForm(MapForm mapForm) {
		this.mapForm = mapForm;
	}

	@RequestMapping("/myprofile")
	public String myprofile(@RequestParam(name = "openid", required = false) String openid, ModelMap map, String AT)
			throws Exception {
		if (StringUtils.isBlank(AT)) {
			AT = this.getAppToken(openid, "", commonService);
		}

		map.put(PxAT, AT);

		headerRending(AT, map);

		map.put("weixinUserInfo", bussService.getWeixinUserInfo(AT));
		map.put("userInfo", bussService.getUserInfo(AT));
		return BasePath + "myprofile";
	}

	@RequestMapping("/saveUserInfo")
	@ResponseBody
	public String saveUserInfo(ModelMap map, String mobile, String email, String AT) throws Exception {
		Map<String, Object> userInfo = new HashMap<>();
		userInfo.put("MOBILE", mobile);
		userInfo.put("EMAIL", email);
		bussService.saveUserInfo(userInfo, AT);
		return "";
	}

}
