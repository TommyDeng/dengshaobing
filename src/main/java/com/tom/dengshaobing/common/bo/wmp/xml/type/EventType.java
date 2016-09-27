package com.tom.dengshaobing.common.bo.wmp.xml.type;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月22日 上午11:28:07
 *
 */

public enum EventType {
	subscribe, // 关注
	unsubscribe, // 取消关注
	SCAN, // 扫码
	LOCATION, // 定位
	CLICK, // 点击事件
	VIEW// 点击链接
}