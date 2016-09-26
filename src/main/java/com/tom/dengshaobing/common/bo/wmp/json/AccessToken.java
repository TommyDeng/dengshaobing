package com.tom.dengshaobing.common.bo.wmp.json;

import java.io.Serializable;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月26日 下午5:48:32
 *
 */

public class AccessToken implements Serializable {
	private static final long serialVersionUID = -7473308181839434498L;

	public String access_token;

	public Long expires_in;

}
