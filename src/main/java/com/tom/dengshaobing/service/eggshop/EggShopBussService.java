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
	/**
	 * 绑定openid,并获取userUC
	 * 
	 * @param openid
	 * @return
	 * @throws Exception
	 */
	UUID getUserUCByOpenid(String openid) throws Exception;

	/**
	 * 查询所有产品列表
	 * 
	 * @return
	 */
	TableMeta listAllProduct();

	/**
	 * 新增产品
	 * 
	 * @param properties
	 * @param userUC
	 * @throws Exception
	 */
	void addProduct(Map<String, Object> properties, UUID userUC) throws Exception;

	/**
	 * 查询产品
	 * 
	 * @param productUC
	 * @param userUC
	 * @throws Exception
	 */
	Map<String, Object> queryProduct(UUID productUC, UUID userUC) throws Exception;

	/**
	 * 查询产品明细
	 * @param productUC
	 * @param userUC
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> queryProductDetail(UUID productUC, UUID userUC) throws Exception;

	/**
	 * 更新产品
	 * 
	 * @param properties
	 * @param userUC
	 * @throws Exception
	 */
	void updateProduct(Map<String, Object> properties, UUID userUC) throws Exception;

	/**
	 * 删除产品
	 * 
	 * @param productUC
	 * @param userUC
	 * @throws Exception 
	 */
	void deleteProduct(UUID productUC, UUID userUC) throws Exception;

	/**
	 * 查询用户订单
	 * 
	 * @param userUC
	 * @return
	 */
	TableMeta listOrderByUserUC(UUID userUC);

	/**
	 * @param properties
	 * @param userUC
	 * @throws Exception
	 */
	void updateProductDetail(Map<String, Object> properties, UUID userUC) throws Exception;

}
