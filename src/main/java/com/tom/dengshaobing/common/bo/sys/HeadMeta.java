package com.tom.dengshaobing.common.bo.sys;

import lombok.Data;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月28日 下午3:10:20
 *
 */
@Data
public class HeadMeta {
	public int index;

	// 含有$H 则不显示
	// 含有$K 代表主键
	// 含有$L 则代表有链接
	public String columnLabel;// 显示在前端页面的名称，可支持中文

	public String columnName;// 字段名列名

	public String className;// 字段类型对于的class

	public boolean display;// 是否显示
	
	public boolean uriAttachable;// 链接
	
	public boolean isKey;// 主键 
	
}
