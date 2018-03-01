package com.tom.dengshaobing.common.bo.sys;

import lombok.Data;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2018年2月2日 上午11:45:41
 *
 */
@Data
public class RestHttpReply {
	public static final String CODE_SUCC = "S000";
	public static final String CODE_FAIL = "S001";
	public static final String CODE_UNKNOWN = "S100";

	public String code;
	public String desc;
	public Object data;
}
