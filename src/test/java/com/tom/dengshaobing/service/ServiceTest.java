package com.tom.dengshaobing.service;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.tom.dengshaobing.common.bo.wmp.Message;
import com.tom.dengshaobing.config.TestConfig;
import com.tom.dengshaobing.config.WebConfig;
import com.tom.dengshaobing.controller.WexinMessagePlatformController;
import com.tom.utils.XMLParseUtils;

import junit.framework.TestSuite;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { TestConfig.class, WebConfig.class }, loader = AnnotationConfigWebContextLoader.class)
public class ServiceTest extends TestSuite {
	@Autowired
	WexinMessagePlatformService WexinMessagePlatformService;
	
	@Autowired
	WexinMessagePlatformController WexinMessagePlatformController;
	
	@Test
	public void checkSignature() {
		String signature = "5c5e1eddb69c2c5d215feddfee46b97cf2fea866";
		String timestamp = "1474297059";
		String nonce = "841823922";
		Assert.assertTrue(WexinMessagePlatformService.checkSignature(signature, timestamp, nonce));
	}

	@Test
	public void getAccessToken() throws Exception {
		Assert.assertNotNull(WexinMessagePlatformService.getAccessToken());
	}

	@Test
	public void createMenu() throws Exception {
		String menuCreateJsonStr = "{\"button\":[{\"type\":\"click\",\"name\":\"今日报送\",\"key\":\"V1001_TODAY_MUSIC\"},{\"name\":\"菜单\",\"sub_button\":[{\"type\":\"view\",\"name\":\"搜索\",\"url\":\"http://www.soso.com/\"},{\"type\":\"view\",\"name\":\"视频\",\"url\":\"http://v.qq.com/\"},{\"type\":\"click\",\"name\":\"赞一下我们\",\"key\":\"V1001_GOOD\"}]}]}";
		WexinMessagePlatformService.createMenu(menuCreateJsonStr);
	}
	
	@Test
	public void wexinMessageAccess() throws Exception {
		String signature = "5c5e1eddb69c2c5d215feddfee46b97cf2fea866";
		String timestamp = "1474297059";
		String nonce = "841823922";
		Message message = XMLParseUtils.generateJavaBean("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><xml><ToUserName>gh_853375ce543c</ToUserName><FromUserName>o49hsxB_Rx71KVRAesfHaZ7WAy40</FromUserName><CreateTime>1474552151</CreateTime><MsgType>event</MsgType><Event>CLICK</Event><EventKey>rselfmenu_3_3</EventKey></xml>", Message.class);
		Message returnMessage = WexinMessagePlatformController.wexinMessageAccess(signature, timestamp, nonce, message);
		System.err.println(returnMessage);
	}

}
