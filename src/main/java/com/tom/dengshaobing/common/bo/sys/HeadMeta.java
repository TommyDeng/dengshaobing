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

	public String columnLabel;// 可支持中文

	public String columnName;// 列名

	public String className;// 字段类型对于的class

	public boolean display;// 是否显示
}
