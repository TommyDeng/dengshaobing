package com.tom.dengshaobing.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.tom.dengshaobing.config.TestConfig;
import com.tom.dengshaobing.config.WebConfig;

import junit.framework.TestSuite;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { TestConfig.class, WebConfig.class }, loader = AnnotationConfigWebContextLoader.class)
public class ServiceTest extends TestSuite {
	@Autowired
	WexinMessagePlatformService WexinMessagePlatformService;

	@Test
	public void checkSignature() {
		String signature = "5c5e1eddb69c2c5d215feddfee46b97cf2fea866";
		String echostr = "2821672319559138803";
		String timestamp = "1474297059";
		String nonce = "841823922";
		String token = "tommydeng";
		Assert.assertTrue(WexinMessagePlatformService.checkSignature(signature, token, timestamp, nonce));
	}

}
