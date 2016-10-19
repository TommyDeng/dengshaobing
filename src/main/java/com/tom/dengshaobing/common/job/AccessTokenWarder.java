package com.tom.dengshaobing.common.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tom.dengshaobing.service.WexinMessagePlatformService;
import com.tom.dengshaobing.service.WexinMessagePlatformService.AccessTokenStatus;

@Component
public class AccessTokenWarder {

	@Autowired
	WexinMessagePlatformService wexinMessagePlatformService;

	@Scheduled(fixedRate = 7200000)
	public void accessTokenFetch() throws Exception {
//		wexinMessagePlatformService.setAccessTokenStatus(AccessTokenStatus.REFETCHING);
//		wexinMessagePlatformService.fetchAccessToken();
//		wexinMessagePlatformService.setAccessTokenStatus(AccessTokenStatus.VALID);
	}
}
