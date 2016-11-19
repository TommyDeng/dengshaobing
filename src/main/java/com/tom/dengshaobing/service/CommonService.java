package com.tom.dengshaobing.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.tom.dengshaobing.common.bo.sys.TableMeta;
import com.tom.dengshaobing.common.bo.wmp.json.Errorable;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月14日 上午11:30:12
 *
 */

public interface CommonService {

	/**
	 * 记录用户登陆日志
	 * 
	 * @param name
	 * @param deviceType
	 * @throws Exception
	 */
	void logVisit(String name, String deviceType) throws Exception;

	/**
	 * 查询所有用户登陆日志
	 * 
	 * @return
	 */
	TableMeta listVisit();

	/**
	 * 记录错误Errorable消息
	 * 
	 * @param uri
	 * @param error
	 * @throws Exception
	 */
	void logErrorable(String uri, Errorable error) throws Exception;

	/**
	 * 为实现restfull 登录或者微信登录以后分配给用户的唯一会话ID
	 * 
	 * @param entranceId
	 * @return
	 * @throws Exception
	 */
	String getAppTokenByEntranceId(String entranceId, String entranceType) throws Exception;

	/**
	 * 使用token获取用户uc
	 * 
	 * @param AT
	 * @param commonService
	 * @return
	 */
	UUID getUserUCByAppToken(String AT);

	/**
	 * 绑定openid,并获取userUC
	 * 
	 * @param AT
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getWXUserInfo(String AT) throws Exception;

	/**
	 * 存储文件并返回UUID
	 * @param thumbnailFile
	 * @return
	 * @throws Exception 
	 */
	UUID storeUploadFile(CommonsMultipartFile thumbnailFile) throws Exception;
	
	

}
