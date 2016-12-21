package com.tom.dengshaobing.service.eggshop;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	public TableMeta listAllProduct() {
		return dataAccessService.queryTableMeta("BUSS004", null);
	}

	@Override
	public void addProduct(Map<String, Object> properties, String AT) throws Exception {
		if (properties == null)
			return;
		UUID userUC = commonService.getUserUCByAppToken(AT);

		String productUC = UUID.randomUUID().toString();
		Map<String, Object> insertParamMap = new HashMap<>();
		insertParamMap.put("UNIQUE_CODE", productUC);
		insertParamMap.put("NAME", properties.get("NAME"));
		insertParamMap.put("PRICE", properties.get("PRICE"));
		insertParamMap.put("BRIEF", properties.get("BRIEF"));
		insertParamMap.put("THUMBNAIL", properties.get("THUMBNAIL"));
		insertParamMap.put("DETAIL_DESCIPTION", properties.get("DETAIL_DESCIPTION"));
		insertParamMap.put("CREATOR", String.valueOf(userUC));
		dataAccessService.insertSingle("ES_PRODUCT", insertParamMap);

	}

	@Override
	public Map<String, Object> queryProduct(UUID productUC, String AT) throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("UNIQUE_CODE", productUC);
		return dataAccessService.queryForOneRow("BUSS006", paramMap);
		// return dataAccessService.queryRowMapById("ES_PRODUCT", productUC);
	}

	@Override
	public void updateProduct(Map<String, Object> properties, String AT) throws Exception {
		dataAccessService.updateSingle("ES_PRODUCT", properties);
	}

	@Override
	public void deleteProduct(UUID productUC, String AT) throws Exception {
		dataAccessService.deleteRowById("ES_PRODUCT", productUC);
	}

	@Override
	public Map<String, Object> queryOrder(UUID orderUC, String AT) throws Exception {
		return dataAccessService.queryForOneRowAllColumn("ES_ORDER", orderUC);
	}

	@Override
	public TableMeta queryOrderItem(UUID orderUC, String AT) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("ORDER_UC", orderUC);
		return dataAccessService.queryTableMeta("BUSS003", paramMap);
	}

	@Override
	public void discardOrder(UUID orderUC, String AT) throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("ORDER_UC", orderUC);
		paramMap.put("STATUS", Const.ORDER_STATUS.Disable);
		dataAccessService.updateSingle("ES_ORDER", paramMap);
	}

	@Override
	public void deleteOrder(UUID orderUC, String AT) throws Exception {
		dataAccessService.deleteRowById("ES_ORDER", orderUC);
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("ORDER_UC", orderUC);
		dataAccessService.update("BUSS005", paramMap);
	}

	@Override
	public List<Map<String, Object>> listAllProductForMain() {
		return dataAccessService.queryMapList("BUSS012", null);
	}

	@Override
	public Map<String, Object> getShoppingCartInfo(String AT) {
		UUID userUC = commonService.getUserUCByAppToken(AT);
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("USER_UC", userUC);
		// 仅查询数量
		BigDecimal cartItemCount = dataAccessService.queryForOneObject("ES_BUSS009", paramMap, BigDecimal.class);
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
		BigDecimal count = dataAccessService.queryForOneObject("ES_BUSS007", paramMap, BigDecimal.class);

		paramMap.put("UNIQUE_CODE", UUID.randomUUID());
		paramMap.put("PRODUCT_COUNT",
				count == null ? new BigDecimal(productCount) : count.add(new BigDecimal(productCount)));
		dataAccessService.update("ES_BUSS008", paramMap);
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
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("UNIQUE_CODE", cartUC);
		paramMap.put("PRODUCT_COUNT", new BigDecimal(productCount));
		paramMap.put("CREATOR", userUC);
		dataAccessService.updateSingle("ES_SHOPPING_CART", paramMap);
		return productCount;
	}

	@Override
	public void deleteCartItem(UUID cartItemUC, String AT) throws Exception {

		dataAccessService.deleteRowById("ES_SHOPPING_CART", cartItemUC);
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
	public Map<String, Object> getUserInfo(String AT) {
		UUID userUC = commonService.getUserUCByAppToken(AT);
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("UNIQUE_CODE", userUC);
		return dataAccessService.queryForOneRow("BUSS013", paramMap);
	}

	@Override
	public Map<String, Object> getWeixinUserInfo(String AT) {
		UUID userUC = commonService.getUserUCByAppToken(AT);
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("UNIQUE_CODE", userUC);
		return dataAccessService.queryForOneRow("BUSS015", paramMap);
	}

	@Override
	public Map<String, Object> getWeixinUserInfoDetail(String AT) {
		UUID userUC = commonService.getUserUCByAppToken(AT);
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("UNIQUE_CODE", userUC);
		return dataAccessService.queryForOneRow("BUSS014", paramMap);
	}

	@Override
	public void saveUserInfo(Map<String, Object> userInfo, String AT) throws Exception {
		UUID userUC = commonService.getUserUCByAppToken(AT);
		userInfo.put("UNIQUE_CODE", userUC);
		dataAccessService.updateSingle("SYS_USER", userInfo);
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
	public void submitOrder(String AT, UUID selectedAddressUC, String paymentType, String ipAddress) {
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
			wexinPaymentService.unifiedOrder(orderUC,ipAddress,AT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
