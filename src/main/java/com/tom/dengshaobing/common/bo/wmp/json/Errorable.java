package com.tom.dengshaobing.common.bo.wmp.json;

import java.io.Serializable;

import lombok.Data;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月27日 下午2:35:15
 *
 */
@Data
public class Errorable implements Serializable {
	private static final long serialVersionUID = -4711801850074489772L;
	public Integer errcode;
	public String errmsg;
}
