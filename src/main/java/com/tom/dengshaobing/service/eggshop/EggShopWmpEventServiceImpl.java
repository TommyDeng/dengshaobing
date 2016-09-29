package com.tom.dengshaobing.service.eggshop;

import org.springframework.stereotype.Service;

import com.tom.dengshaobing.common.bo.wmp.xml.MessageXml;
import com.tom.dengshaobing.common.bo.wmp.xml.type.EventType;
import com.tom.dengshaobing.common.bo.wmp.xml.type.MessageType;
import com.tom.dengshaobing.service.WmpEventService;
import com.tom.utils.StoredConfigUtils;

/**
 * 
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月22日 下午2:35:04
 *
 */

@Service
public class EggShopWmpEventServiceImpl extends WmpEventService {
	@Override
	public MessageXml processInput(MessageXml messageIn) {
		MessageXml messageOut = new MessageXml();

		// swap message to user
		messageOut.ToUserName = messageIn.FromUserName;
		messageOut.FromUserName = messageIn.ToUserName;
		messageOut.CreateTime = System.currentTimeMillis();

		// 根据不同消息类型做处理
		MessageType msgType = messageIn.MsgType;
		if (MessageType.text.equals(msgType)) {
			messageOut.Content = "暂时不支持普通消息发送,请点击菜单进行操作.谢谢!";
		} else if (MessageType.event.equals(msgType)) {

			// 关注
			EventType eventType = messageIn.Event;
			if (EventType.subscribe.equals(eventType)) {
				messageOut.MsgType = MessageType.text;
				messageOut.MsgId = messageIn.MsgId;
				messageOut.Content = "欢迎关注本公众号!";
			}

		} else {
			messageOut.MsgType = MessageType.text;
			messageOut.MsgId = messageIn.MsgId;
			messageOut.Content = "OK, got it!";
		}
		return messageOut;
	};

	@Override
	public MessageXml processMenu_1_1(MessageXml message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageXml processMenu_1_2(MessageXml message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageXml processMenu_1_3(MessageXml message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageXml processMenu_1_4(MessageXml message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageXml processMenu_1_5(MessageXml message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageXml processMenu_2_1(MessageXml message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageXml processMenu_2_2(MessageXml message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageXml processMenu_2_3(MessageXml message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageXml processMenu_2_4(MessageXml message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageXml processMenu_2_5(MessageXml message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageXml processMenu_3_1(MessageXml message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageXml processMenu_3_2(MessageXml message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageXml processMenu_3_3(MessageXml message) {
		// 查询当前vote
		String voteGood = StoredConfigUtils.getValue("Application.VoteGood");
		Integer voteGoodInt = Integer.parseInt(voteGood);
		voteGoodInt = voteGoodInt + 1;
		StoredConfigUtils.setValue("Application.VoteGood", voteGoodInt.toString());
		MessageXml returnMessage = new MessageXml();
		returnMessage.ToUserName = message.FromUserName;
		returnMessage.FromUserName = message.ToUserName;
		returnMessage.CreateTime = System.currentTimeMillis();
		returnMessage.MsgType = MessageType.text;
		returnMessage.Content = "Thanks! current votes: " + voteGoodInt;
		return returnMessage;
	}

	@Override
	public MessageXml processMenu_3_4(MessageXml message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageXml processMenu_3_5(MessageXml message) {
		// TODO Auto-generated method stub
		return null;
	}

}
