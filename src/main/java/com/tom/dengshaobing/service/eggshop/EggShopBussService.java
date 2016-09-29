package com.tom.dengshaobing.service.eggshop;

import com.tom.dengshaobing.common.bo.sys.TableMeta;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月29日 上午10:33:46
 *
 */

public interface EggShopBussService {
	String getUserUCByOpenid(String openid);

	TableMeta listOrderByUserUC(String userUC);

	TableMeta listAllProduct();

}
