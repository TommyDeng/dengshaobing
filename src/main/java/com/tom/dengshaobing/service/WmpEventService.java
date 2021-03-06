package com.tom.dengshaobing.service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import com.tom.dengshaobing.common.bo.wmp.type.EventType;
import com.tom.dengshaobing.common.bo.wmp.type.MessageType;
import com.tom.dengshaobing.common.bo.wmp.xml.MessageXml;

/**
 * 公众号业务处理服务器类
 * 
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月22日 下午2:32:30
 *
 */
public abstract class WmpEventService {
	/**
	 * 非菜单点击类消息
	 * 
	 * @param message
	 * @return
	 */
	public MessageXml processInput(MessageXml messageIn) {
		MessageXml messageOut = new MessageXml();

		// swap message to user
		messageOut.ToUserName = messageIn.FromUserName;
		messageOut.FromUserName = messageIn.ToUserName;
		messageOut.CreateTime = System.currentTimeMillis();

		// 根据不同消息类型做处理
		MessageType msgType = messageIn.MsgType;
		if (MessageType.text.equals(msgType)) {
			uglyRobotForText(messageIn, messageOut);
		} else if (MessageType.event.equals(msgType)) {

			// 关注
			EventType eventType = messageIn.Event;
			if (EventType.subscribe.equals(eventType)) {
				messageOut.MsgType = MessageType.text;
				messageOut.MsgId = messageIn.MsgId;
				messageOut.Content = "Welcome!";
			}

		} else {
			messageOut.MsgType = MessageType.text;
			messageOut.MsgId = messageIn.MsgId;
			messageOut.Content = "OK, got it!";
		}
		// else if (MessageType.image.equals(msgType)) {
		// } else if (MessageType.voice.equals(msgType)) {
		// } else if (MessageType.video.equals(msgType)) {
		// } else if (MessageType.event.equals(msgType)) {
		// } else if (MessageType.location.equals(msgType)) {
		// } else if (MessageType.link.equals(msgType)) {
		// } else if (MessageType.news.equals(msgType)) {
		// }

		return messageOut;
	};

	Random randomizer = new Random();
	static final List<String> provocativeDefeatList = Arrays.asList(new String[] { "妈蛋", "fk u", "滚犊子", "再说一遍,劳资踢了你" });
	static final List<String> unknowReplyList = Arrays.asList(
			new String[] { "暗号不对.", "@#%$@_@&!Y....", "无言以对...", "看不懂.", "看不懂啊,哥", "这是啥?", "噢,知道了", "......", "呵呵" });
	static final List<String> curiousBabies = Arrays.asList(new String[] { "你想太多了.", "你特么逗我呢" });

	private void uglyRobotForText(MessageXml messageIn, MessageXml messageOut) {
		messageOut.MsgType = MessageType.text;
		if (StringUtils.isEmpty(messageIn.Content))
			return;

		String inContent = messageIn.Content.toLowerCase();
		// 表情 :/ ==> X10
		if (inContent.startsWith("/:")) {
			String outContent = "";
			for (int i = 0; i < 10; i++) {
				outContent += messageIn.Content;
			}
			messageOut.Content = outContent;
			return;
		}

		// 打招呼
		if (inContent.contains("你好") || inContent.contains("hello") || inContent.contains("hi")
				|| inContent.contains("hi")) {
			messageOut.Content = "你好~";
			return;
		}
		// 挑衅反击
		if (inContent.contains("你妈") || inContent.contains("操你") || inContent.contains("fuck")
				|| inContent.contains("他妈") || inContent.contains("日")) {
			messageOut.Content = provocativeDefeatList.get(randomizer.nextInt(provocativeDefeatList.size()));
			return;
		}

		// 好奇宝宝
		if (inContent.contains("芝麻开门") || inContent.contains("天王盖")) {
			messageOut.Content = curiousBabies.get(randomizer.nextInt(curiousBabies.size()));
			return;
		}

		// 别的都看不懂--!,随便敷衍一下吧
		messageOut.Content = unknowReplyList.get(randomizer.nextInt(unknowReplyList.size()));

		return;

	}

	public abstract MessageXml processMenu_1_1(MessageXml message);

	public abstract MessageXml processMenu_1_2(MessageXml message);

	public abstract MessageXml processMenu_1_3(MessageXml message);

	public abstract MessageXml processMenu_1_4(MessageXml message);

	public abstract MessageXml processMenu_1_5(MessageXml message);

	public abstract MessageXml processMenu_2_1(MessageXml message);

	public abstract MessageXml processMenu_2_2(MessageXml message);

	public abstract MessageXml processMenu_2_3(MessageXml message);

	public abstract MessageXml processMenu_2_4(MessageXml message);

	public abstract MessageXml processMenu_2_5(MessageXml message);

	public abstract MessageXml processMenu_3_1(MessageXml message);

	public abstract MessageXml processMenu_3_2(MessageXml message);

	public abstract MessageXml processMenu_3_3(MessageXml message);

	public abstract MessageXml processMenu_3_4(MessageXml message);

	public abstract MessageXml processMenu_3_5(MessageXml message);

}
