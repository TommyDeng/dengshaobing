package com.tom.dengshaobing.common.job;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tom.dengshaobing.service.DataAccessService;
import com.tom.dengshaobing.service.WexinMessagePlatformService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HealthyPulse {

	@Autowired
	DataAccessService dataAccessService;

	@Autowired
	WexinMessagePlatformService wexinMessagePlatformService;

	@Scheduled(fixedRate = 1200000)
	public void printPulse() {
		log.info("Server Pulse.");
		log.info("CurrentAccessToken : " + wexinMessagePlatformService.getCurrentAccessToken().toString());
	}

	@Scheduled(fixedRate = 28800000)
	public void selfClean() throws Exception {
		log.info("App Self Clean.");
		// insert admin user
		long count = dataAccessService.queryForOneObject("SYS003", null, Long.class);
		if (count == 0l) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("UNIQUE_CODE", UUID.fromString("00000000-0000-0000-0000-000000000001"));
			paramMap.put("NAME", "admin");
			paramMap.put("MOBILE", "18888888888");
			paramMap.put("EMAIL", "admin@dengshaobing.com");
			paramMap.put("TYPE", "1");
			paramMap.put("OPENID", "1");
			paramMap.put("STATUS", "1");
			paramMap.put("REMARK", "test");
			paramMap.put("CREATOR", "admin");
			dataAccessService.insertSingle("TX_USER", paramMap);
		}

		count = dataAccessService.queryForOneObject("SYS004", null, Long.class);
		if (count == 0l) {

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("UNIQUE_CODE", UUID.fromString("00000000-0000-0000-0000-000000000001"));
			paramMap.put("OPENID", "1");
			paramMap.put("NICKNAME", "Admin");
			paramMap.put("SEX", "1");
			paramMap.put("CITY", "衡阳");
			paramMap.put("COUNTRY", "中国");
			paramMap.put("PROVINCE", "湖南");
			paramMap.put("LANGUAGE", "中文");
			paramMap.put("HEADIMGURL", "https://avatars3.githubusercontent.com/u/7498513?v=3&s=40");
			paramMap.put("UNIONID", "1");
			paramMap.put("GROUPID", "1");
			paramMap.put("REMARK", "1");

			dataAccessService.insertSingle("TX_USERINFO_WX", paramMap);
		}
	}
}
