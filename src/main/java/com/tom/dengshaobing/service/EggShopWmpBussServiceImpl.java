package com.tom.dengshaobing.service;

import org.springframework.stereotype.Service;

import com.tom.dengshaobing.common.bo.wmp.xml.MessageXml;
import com.tom.dengshaobing.common.bo.wmp.xml.type.MessageType;
import com.tom.utils.StoredConfigUtils;

/**
 * 
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月22日 下午2:35:04
 *
 */

@Service
public class EggShopWmpBussServiceImpl extends WmpBussService {

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
