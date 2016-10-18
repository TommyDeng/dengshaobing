package com.tom.dengshaobing.service.eggshop;

import java.util.List;
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
	void addProduct(Map<String, Object> properties, String appToken) throws Exception;

	/**
	 * 查询产品
	 * 
	 * @param productUC
	 * @param userUC
	 * @throws Exception
	 */
	Map<String, Object> queryProduct(UUID productUC, String appToken) throws Exception;

	/**
	 * 查询产品明细
	 * 
	 * @param productUC
	 * @param userUC
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> queryProductDetail(UUID productUC, String appToken) throws Exception;

	/**
	 * 更新产品
	 * 
	 * @param properties
	 * @param userUC
	 * @throws Exception
	 */
	void updateProduct(Map<String, Object> properties, String appToken) throws Exception;

	/**
	 * 更新产品明细
	 * 
	 * @param properties
	 * @param userUC
	 * @throws Exception
	 */
	void updateProductDetail(Map<String, Object> properties, String appToken) throws Exception;

	/**
	 * 删除产品
	 * 
	 * @param productUC
	 * @param userUC
	 * @throws Exception
	 */
	void deleteProduct(UUID productUC, String appToken) throws Exception;

	/**
	 * 查询用户订单
	 * 
	 * @param userUC
	 * @return
	 */
	TableMeta listOrder(String appToken);

	/**
	 * 查询order
	 * 
	 * @param orderUC
	 * @param userUC
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> queryOrder(UUID orderUC, String appToken) throws Exception;

	/**
	 * 查询order子项
	 * 
	 * @param orderUC
	 * @param userUC
	 * @return
	 */
	TableMeta queryOrderItem(UUID orderUC, String appToken);

	/**
	 * 废弃order
	 * 
	 * @param orderUC
	 * @param userUC
	 * @throws Exception
	 */
	void discardOrder(UUID orderUC, String appToken) throws Exception;

	/**
	 * 删除order
	 * 
	 * @param orderUC
	 * @param userUC
	 * @throws Exception
	 */
	void deleteOrder(UUID orderUC, String appToken) throws Exception;

	/**
	 * 获取所有产品(main页面展示)
	 * 
	 * @return
	 */
	List<Map<String, Object>> listAllProductForMain();

	/**
	 * 获取cart信息
	 * 
	 * @param appToken
	 * @return
	 */
	Map<String, Object> getShoppingCartInfo(String appToken);

	/**
	 * 添加到cart
	 * 
	 * @param productUC
	 * @param productCount
	 * @param appToken
	 * @return
	 */
	Long addItemShoppingCart(UUID productUC, Long productCount, String appToken);

	/**
	 * 获取cart信息
	 * 
	 * @param appToken
	 * @return
	 */
	TableMeta listShoppingCart(String appToken);

	/**
	 * 变更cart项数量
	 * @param fromString
	 * @param parseInt
	 * @param aT
	 * @return
	 * @throws Exception 
	 */
	Long changeItemQtyShoppingCart(UUID cartUC, Long productCount, String appToken) throws Exception;

}
