package com.tom.dengshaobing.service;

import org.springframework.stereotype.Service;

import com.tom.dengshaobing.common.bo.wmp.Message;
import com.tom.dengshaobing.common.bo.wmp.type.MessageType;
import com.tom.utils.StoredConfigUtils;

/**
 * 
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月22日 下午2:35:04
 *
 */

@Service
public class EggShopWmpBussServiceImpl implements WmpBussService {

	@Override
	public Message processMenu_1_1(Message message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message processMenu_1_2(Message message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message processMenu_1_3(Message message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message processMenu_1_4(Message message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message processMenu_1_5(Message message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message processMenu_2_1(Message message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message processMenu_2_2(Message message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message processMenu_2_3(Message message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message processMenu_2_4(Message message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message processMenu_2_5(Message message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message processMenu_3_1(Message message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message processMenu_3_2(Message message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message processMenu_3_3(Message message) {
		// 查询当前vote
		String voteGood = StoredConfigUtils.getValue("Application.VoteGood");
		Integer voteGoodInt = Integer.parseInt(voteGood);
		voteGoodInt = voteGoodInt + 1;
		StoredConfigUtils.setValue("Application.VoteGood", voteGoodInt.toString());
		Message returnMessage = new Message();
		returnMessage.ToUserName = message.FromUserName;
		returnMessage.FromUserName = message.ToUserName;
		returnMessage.CreateTime = System.currentTimeMillis();
		returnMessage.MsgType = MessageType.text;
		returnMessage.Content = "Thanks! current votes: " + voteGoodInt;
		return returnMessage;
	}

	@Override
	public Message processMenu_3_4(Message message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message processMenu_3_5(Message message) {
		// TODO Auto-generated method stub
		return null;
	}

}
