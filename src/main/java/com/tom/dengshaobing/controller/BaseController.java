package com.tom.dengshaobing.controller;

import com.tom.dengshaobing.service.CommonService;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月28日 下午5:30:03
 *
 */
public class BaseController {

	protected final String SxTableMeta = "tableMeta";// 页面tableMeta变量

	protected final String SxFormData = "formData";// 页面formData变量

	protected final String SxMapList = "mapList";// 页面mapList变量

	protected final String PxOpenid = "openid";// openid:当前操作用户openid

	protected final String PxUserUC = "userUC";// userUC:当前操作用户ID

	protected final String PxRowUC = "rowUC";// rowUC:记录ID

	public String getAppToken(String entranceId, CommonService commonService) {
		// bussService.getUserUCByOpenid(openid);
		return commonService.getAppToken(entranceId);
	}
}
