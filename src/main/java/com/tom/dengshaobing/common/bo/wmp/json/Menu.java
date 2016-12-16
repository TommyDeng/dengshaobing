package com.tom.dengshaobing.common.bo.wmp.json;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月21日 下午12:00:54
 *
 */
@Data
public class Menu implements Serializable {
	private static final long serialVersionUID = -8440708456219817892L;
	public List<Button> button;// ✓
}
