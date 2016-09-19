package com.tom.dengshaobing.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tom.dengshaobing.service.WexinMessagePlatformService;

@Component
public class AccessTokenWarder {

	@Autowired
	WexinMessagePlatformService wexinMessagePlatformService;

	@Scheduled(fixedRate = 7200000)
	public void accessTokenFetch() throws Exception {
		wexinMessagePlatformService.fetchAccessToken();
	}
}
