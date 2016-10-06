package com.tom.dengshaobing.controller;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.tom.dengshaobing.service.CommonService;
import com.tom.dengshaobing.service.eggshop.EggShopBussService;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月28日 下午5:30:03
 *
 */
@Component
public class BaseController {

	@Autowired
	CommonService commonService;

	@Autowired
	EggShopBussService bussService;

	protected final String SxTableMeta = "tableMeta";// 页面tableMeta变量

	protected final String SxFormData = "formData";// 页面formData变量

	protected final String SxMapList = "mapList";// 页面mapList变量

	protected final String PxOpenid = "openid";// openid:当前操作用户openid

	protected final String PxUserUC = "userUC";// userUC:当前操作用户ID

	protected final String PxRowUC = "rowUC";// rowUC:记录ID

	protected final String PxAT = "AT"; // appToken

	HttpServletRequest request;
	HttpServletResponse response;

	// 加载用户信息和count
	public void headerRending(String appToken, ModelMap map) throws Exception {
		Map<String, Object> userInfo = commonService.getWXUserInfo(appToken);
		Map<String, Object> cartInfo = bussService.getShoppingCartInfo(appToken);
		map.put("userInfo", userInfo);
		map.put("cartInfo", cartInfo);
	}

	public String getAppToken(String entranceId, String entranceType, CommonService commonService) throws Exception {
		return commonService.getAppTokenByEntranceId(entranceId, entranceType);
	}
}
