package com.tom.dengshaobing.service;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.tom.dengshaobing.common.bo.sys.TableMeta;
import com.tom.dengshaobing.common.bo.wmp.json.Errorable;
import com.tom.dengshaobing.service.eggshop.EggShopBussService;
import com.tom.dengshaobing.sqlstatements.SqlStatements;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月14日 上午11:31:23
 *
 */
@Service
public class CommonServiceImpl implements CommonService {

	@Autowired
	private Environment env;

	@Autowired
	DataAccessService dataAccessService;

	@Autowired
	EggShopBussService bussService;

	@Autowired
	ServletContext servletContext;

	@Override
	@Transactional
	public void logVisit(String visitorName, String deviceType) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name".toUpperCase(), visitorName);
		paramMap.put("remark".toUpperCase(), deviceType);
		dataAccessService.insertSingle("LOG_VISIT", paramMap);
	}

	@Override
	public TableMeta listVisit() {
		return dataAccessService.queryTableMeta(SqlStatements.get("002"), new HashMap<>());
	}

	@Override
	public void logErrorable(String uri, Errorable error) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("uri".toUpperCase(), uri);
		paramMap.put("errorable".toUpperCase(), error.toString());
		paramMap.put("create_time".toUpperCase(), Calendar.getInstance());
		dataAccessService.insertSingle("LOG_ERRORABLE", paramMap);
	}

	@Override
	public String getAppToken(String visitId, String visitType) throws Exception {
		// 简单实现,使用USER_UC作为AT
		if (visitId == null) {
			return visitId;
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("OPENID", visitId);
		UUID userUC = dataAccessService.queryForOneObject("SYS006", paramMap, UUID.class);

		// 暂不实现
		// //微信登录
		// if(VISIT_TYPE.Weixin.equals(visitType)){
		// }
		// //网页登陆
		// else{
		// }

		return userUC == null ? null : userUC.toString();
	}

	@Override
	public UUID getUserUCByAppToken(String AT) {
		// 暂时使用USER_UC作为AT,所以直接返回UUID类型即可
		if (AT == null)
			return null;
		return UUID.fromString(AT);
	}

	@Override
	public Map<String, Object> getWXUserInfo(String AT) throws Exception {
		UUID userUC = getUserUCByAppToken(AT);
		return dataAccessService.queryForOneRowAllColumn("SYS_USERINFO_WX", userUC);
	}

	@Override
	public UUID storeUploadFile(CommonsMultipartFile thumbnailFile) throws Exception {
		UUID storedUUID = UUID.randomUUID();
		String extendsion = FilenameUtils.getExtension(thumbnailFile.getOriginalFilename());

		// restore
		String filePath = env.getProperty("MultimediaServer.Store.BaseFolder")
				+ env.getProperty("MultimediaServer.Retrieve.Prefix") + storedUUID.toString()
				+ FilenameUtils.EXTENSION_SEPARATOR + extendsion;
		// File destinationFile = new
		// File(servletContext.getRealPath(filePath));
		File destinationFile = new File(filePath);
		FileUtils.copyInputStreamToFile(thumbnailFile.getInputStream(), new File(filePath));

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("UNIQUE_CODE", storedUUID);
		paramMap.put("NAME", thumbnailFile.getOriginalFilename());

		String retrievePath = env.getProperty("MultimediaServer.Retrieve.Prefix") + storedUUID.toString()
				+ FilenameUtils.EXTENSION_SEPARATOR + extendsion;

		paramMap.put("PATH", retrievePath);// Retrieve
		paramMap.put("FILE_EXTENSION", extendsion);
		paramMap.put("FILE_SIZE", thumbnailFile.getSize());
		paramMap.put("REMARK", thumbnailFile.getContentType() + ";" + destinationFile.getAbsolutePath());

		dataAccessService.insertSingle("SYS_FILE_STORE_MAPPING", paramMap);
		return storedUUID;
	}

}
