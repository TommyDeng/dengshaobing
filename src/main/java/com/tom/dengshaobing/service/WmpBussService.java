package com.tom.dengshaobing.service;

import com.tom.dengshaobing.common.bo.wmp.Message;

/**
 * 公众号业务处理服务器类
 * 
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月22日 下午2:32:30
 *
 */
public interface WmpBussService {

	Message processMenu_1_1(Message message);

	Message processMenu_1_2(Message message);

	Message processMenu_1_3(Message message);

	Message processMenu_1_4(Message message);

	Message processMenu_1_5(Message message);

	Message processMenu_2_1(Message message);

	Message processMenu_2_2(Message message);

	Message processMenu_2_3(Message message);

	Message processMenu_2_4(Message message);

	Message processMenu_2_5(Message message);

	Message processMenu_3_1(Message message);

	Message processMenu_3_2(Message message);

	Message processMenu_3_3(Message message);

	Message processMenu_3_4(Message message);

	Message processMenu_3_5(Message message);

}
