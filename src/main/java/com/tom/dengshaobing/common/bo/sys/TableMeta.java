package com.tom.dengshaobing.common.bo.sys;

import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月28日 上午10:23:24
 *
 */
@Data
public class TableMeta {
	public String title;

	public List<HeadMeta> headList;
	
	public List<Map<String, Object>> dataList;
}
