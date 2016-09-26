package com.tom.dengshaobing.common.bo.wmp.xml;

import java.io.Serializable;
import java.util.List;

import com.tom.dengshaobing.common.bo.wmp.xml.type.ButtonType;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月21日 下午12:06:41
 *
 */
public class Button implements Serializable {
	private static final long serialVersionUID = 8054520770200896447L;
	public ButtonType type;
	public String name;
	public String key;
	public String url;
	public String media_id;
	public List<Button> sub_button;
}