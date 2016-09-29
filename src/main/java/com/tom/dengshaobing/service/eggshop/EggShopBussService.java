package com.tom.dengshaobing.service.eggshop;

import java.util.Map;
import java.util.UUID;

import com.tom.dengshaobing.common.bo.sys.TableMeta;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月29日 上午10:33:46
 *
 */

public interface EggShopBussService {
	UUID getUserUCByOpenid(String openid);

	TableMeta listOrderByUserUC(UUID userUC);

	TableMeta listAllProduct();

	void addProduct(Map<String, Object> properties, UUID userUC);

}
