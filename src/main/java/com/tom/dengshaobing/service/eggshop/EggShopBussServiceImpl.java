package com.tom.dengshaobing.service.eggshop;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.tom.dengshaobing.common.Const;
import com.tom.dengshaobing.common.bo.sys.TableMeta;
import com.tom.dengshaobing.service.CommonService;
import com.tom.dengshaobing.service.DataAccessService;
import com.tom.dengshaobing.service.WexinPaymentService;

/**
 * 
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月22日 下午2:35:04
 *
 */

@Service
public class EggShopBussServiceImpl implements EggShopBussService {

	@Autowired
	DataAccessService dataAccessService;

	@Autowired
	CommonService commonService;

	@Autowired
	WexinPaymentService wexinPaymentService;

	@Override
	public List<Map<String, Object>> getOrderList(String AT, String orderStatus) {
		UUID userUC = commonService.getUserUCByAppToken(AT);
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("USER_UC", userUC);
		paramMap.put("STATUS", orderStatus);
		List<Map<String, Object>> orderList = dataAccessService.queryMapList("ES_BUSS019", paramMap);
		if (!CollectionUtils.isEmpty(orderList)) {
			for (Map<String, Object> order : orderList) {

				// 订单明细项
				Map<String, Object> itemQueryParamMap = new HashMap<>();
				itemQueryParamMap.put("ORDER_UC", order.get("UNIQUE_CODE"));
				List<Map<String, Object>> orderItemList = dataAccessService.queryMapList("ES_BUSS020",
						itemQueryParamMap);
				order.put("itemList", orderItemList);
			}
		}
		return orderList;
	}

	@Override
	public void discardOrder(UUID orderUC, String AT) throws Exception {
		Map<String, Object> setParamMap = new HashMap<>();
		Map<String, Object> whereParamMap = new HashMap<>();
		whereParamMap.put("ORDER_UC", orderUC);
		setParamMap.put("STATUS", Const.ORDER_STATUS.Disable);
		dataAccessService.updateSingle("ES_ORDER", setParamMap, whereParamMap);
	}

	@Override
	public void deleteOrder(UUID orderUC, String AT) throws Exception {
		Map<String, Object> whereParamMap = new HashMap<>();
		whereParamMap.put("UNIQUE_CODE", orderUC);
		dataAccessService.deleteSingle("ES_ORDER", whereParamMap);
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("ORDER_UC", orderUC);
		dataAccessService.update("ES_BUSS028", paramMap);
	}

	@Override
	public Map<String, Object> getShoppingCartInfo(String AT) {
		UUID userUC = commonService.getUserUCByAppToken(AT);
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("USER_UC", userUC);
		// 仅查询数量
		BigDecimal cartItemCount = dataAccessService.queryForObject("ES_BUSS009", paramMap, BigDecimal.class);
		paramMap.put("CART_COUNT", cartItemCount.longValue());
		return paramMap;
	}

	@Override
	public Long addItemShoppingCart(UUID productUC, Long productCount, String AT) {
		UUID userUC = commonService.getUserUCByAppToken(AT);
		// 查询此种商品在cart表中数量
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("USER_UC", userUC);
		paramMap.put("PRODUCT_UC", productUC);
		Map<String, Object> cartRow = dataAccessService.queryForMap("ES_BUSS007", paramMap);

		if (cartRow == null) {
			paramMap.put("UNIQUE_CODE", UUID.randomUUID());
			paramMap.put("PRODUCT_COUNT", new BigDecimal(productCount));
		} else {
			paramMap.put("UNIQUE_CODE", cartRow.get("UNIQUE_CODE"));
			paramMap.put("PRODUCT_COUNT",
					((BigDecimal) cartRow.get("PRODUCT_COUNT")).add(new BigDecimal(productCount)));
		}

		Set<String> conflictColumns = new TreeSet<>();
		conflictColumns.add("UNIQUE_CODE");
		dataAccessService.upsertSingle("ES_SHOPPING_CART", paramMap, conflictColumns);

		// 重新查询数量
		Map<String, Object> cartInfoMap = getShoppingCartInfo(AT);
		return (Long) cartInfoMap.get("CART_COUNT");
	}

	@Override
	public List<Map<String, Object>> listShoppingCart(String AT) {
		UUID userUC = commonService.getUserUCByAppToken(AT);
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("USER_UC", userUC);

		return dataAccessService.queryMapList("ES_BUSS010", paramMap);
	}

	@Override
	public Long changeCartItemQty(UUID cartUC, Long productCount, String AT) throws Exception {
		UUID userUC = commonService.getUserUCByAppToken(AT);
		Map<String, Object> setParamMap = new HashMap<>();
		Map<String, Object> whereParamMap = new HashMap<>();
		whereParamMap.put("UNIQUE_CODE", cartUC);
		setParamMap.put("PRODUCT_COUNT", new BigDecimal(productCount));
		setParamMap.put("CREATOR", userUC);
		dataAccessService.updateSingle("ES_SHOPPING_CART", setParamMap, whereParamMap);
		return productCount;
	}

	@Override
	public void deleteCartItem(UUID cartItemUC, String AT) throws Exception {
		Map<String, Object> whereParamMap = new HashMap<>();
		whereParamMap.put("UNIQUE_CODE", cartItemUC);
		dataAccessService.deleteSingle("ES_SHOPPING_CART", whereParamMap);
	}

	@Override
	public void selectCartItem(UUID cartItemUC, String AT) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("UNIQUE_CODE", cartItemUC);
		dataAccessService.update("ES_BUSS011", paramMap);
	}

	@Override
	public void selectAllCartItem(String AT, boolean selected) {
		UUID userUC = commonService.getUserUCByAppToken(AT);
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("USER_UC", userUC);
		paramMap.put("SELECTED", selected);
		dataAccessService.update("ES_BUSS012", paramMap);
	}

	@Override
	public Map<String, Object> getWeixinUserInfo(String AT) {
		UUID userUC = commonService.getUserUCByAppToken(AT);
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("UNIQUE_CODE", userUC);
		return dataAccessService.queryForMapAllColumn("SYS_USERINFO_WX", paramMap);
	}

	@Override
	public Map<String, Object> getOrderCountInfo(String AT) {
		UUID userUC = commonService.getUserUCByAppToken(AT);
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("USER_UC", userUC);

		List<Map<String, Object>> result = dataAccessService.queryMapList("ES_BUSS027", paramMap);

		Map<String, Object> returnMap = new HashMap<>();
		// H2不支持行转列，做一遍循环转Map
		if (result != null && result.size() > 0) {
			for (Map<String, Object> row : result) {
				returnMap.put((String) row.get("ORDER_STATUS"), row.get("ORDER_COUNT"));
			}
		}
		return returnMap;
	}

	@Override
	public List<Map<String, Object>> getUserDeliveryAddressList(String AT) {
		UUID userUC = commonService.getUserUCByAppToken(AT);
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("USER_UC", userUC);
		return dataAccessService.queryMapList("ES_BUSS013", paramMap);
	}

	@Override
	public List<Map<String, Object>> getSelectedItemList(String AT) {
		UUID userUC = commonService.getUserUCByAppToken(AT);
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("USER_UC", userUC);
		return dataAccessService.queryMapList("ES_BUSS014", paramMap);
	}

	@Override
	public UUID submitOrder(String AT, UUID selectedAddressUC, String paymentType, String ipAddress) {
		UUID userUC = commonService.getUserUCByAppToken(AT);

		UUID orderUC = UUID.randomUUID();

		// order item
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("USER_UC", userUC);
		paramMap.put("ORDER_UC", orderUC);
		dataAccessService.update("ES_BUSS015", paramMap);

		// order
		paramMap.put("STATUS", Const.ORDER_STATUS.WaitToPay);
		paramMap.put("PAYMENT_TYPE", paymentType);
		paramMap.put("ADDRESS_UC", selectedAddressUC);
		dataAccessService.update("ES_BUSS016", paramMap);

		// delivery address
		paramMap = new HashMap<>();
		paramMap.put("UNIQUE_CODE", selectedAddressUC);
		dataAccessService.update("ES_BUSS017", paramMap);

		// remove from cart
		paramMap = new HashMap<>();
		paramMap.put("USER_UC", userUC);
		dataAccessService.update("ES_BUSS018", paramMap);

		// payment
		try {
			wexinPaymentService.unifiedOrder(orderUC, ipAddress, AT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orderUC;
	}
}
