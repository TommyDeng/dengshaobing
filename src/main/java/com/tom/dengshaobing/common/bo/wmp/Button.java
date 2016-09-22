package com.tom.dengshaobing.common.bo.wmp;

import java.util.List;

import com.tom.dengshaobing.common.bo.wmp.type.ButtonType;

import lombok.Data;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月21日 下午12:06:41
 *
 */
@Data
public class Button {
	private ButtonType type;
	private String name;
	private String key;
	private String url;
	private String media_id;
	private List<Button> sub_button;

}
