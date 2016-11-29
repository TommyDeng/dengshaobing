package com.tom.dengshaobing.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
	protected CommonService commonService;

	protected final String SxTableMeta = "tableMeta";// 页面tableMeta变量(表格数据列表:TableMeta)

	protected final String SxFormData = "formData";// 页面formData变量(新增，修改时的form:MapForm)

	protected final String SxMapList = "mapList";// 页面mapList变量(简单数据列表:List<Map<String,
													// Object>>)

	protected final String PxAT = "AT"; // AppToken

	/**
	 * 初始化页面变量AT，并传递
	 * 
	 * @param openid
	 * @param map
	 * @throws Exception
	 */
	public void pageInit(String AT, String openid, ModelMap map) throws Exception {
		if (StringUtils.isBlank(AT)) {
			AT = this.getAppToken(openid, "", commonService);
		}
		map.put(PxAT, AT);
	}

	public String getAppToken(String entranceId, String entranceType, CommonService commonService) throws Exception {
		return commonService.getAppTokenByEntranceId(entranceId, entranceType);
	}

	/**
	 * 入口处获得App Token
	 * 
	 * @param entranceId
	 * @param entranceType
	 * @param commonService
	 * @return
	 * @throws Exception
	 */
	public String getAppTokenByEntrance(String entranceId, String entranceType, CommonService commonService)
			throws Exception {
		return commonService.getAppTokenByEntranceId(entranceId, entranceType);
	}
}
