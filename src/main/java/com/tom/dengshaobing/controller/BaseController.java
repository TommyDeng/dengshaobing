package com.tom.dengshaobing.controller;

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

	protected final String PxOpenid = "openid";// openid:当前操作用户openid

	protected final String PxUserUC = "userUC";// userUC:当前操作用户ID

	protected final String PxRowUC = "rowUC";// rowUC:记录ID

	protected final String PxAT = "AT"; // AppToken

	protected final String Px = "AT"; // AppToken

	HttpServletRequest request;
	HttpServletResponse response;

	// 加载用户信息和count
	public void pageInit(String AT, String openid, ModelMap map) throws Exception {
		if (StringUtils.isBlank(AT)) {
			AT = this.getAppToken(openid, "", commonService);
		}
		map.put(PxAT, AT);

		Map<String, Object> userInfo = commonService.getWXUserInfo(AT);
		map.put("userInfo", userInfo);
	}

	public String getAppToken(String entranceId, String entranceType, CommonService commonService) throws Exception {
		return commonService.getAppTokenByEntranceId(entranceId, entranceType);
	}
}
