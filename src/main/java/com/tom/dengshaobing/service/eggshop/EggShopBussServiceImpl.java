package com.tom.dengshaobing.service.eggshop;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.tom.dengshaobing.common.Const;
import com.tom.dengshaobing.common.bo.sys.TableMeta;
import com.tom.dengshaobing.service.CommonService;
import com.tom.dengshaobing.service.DataAccessService;

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

	@Override
	public TableMeta listOrder(String appToken) {
		UUID userUC = commonService.getUserUCByAppToken(appToken);
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("USER_UC", userUC);

		return dataAccessService.queryTableMeta("BUSS002", paramMap);
	}

	@Override
	public TableMeta listAllProduct() {
		return dataAccessService.queryTableMeta("BUSS004", null);
	}

	@Override
	public void addProduct(Map<String, Object> properties, String appToken) throws Exception {
		if (properties == null)
			return;
		UUID userUC = commonService.getUserUCByAppToken(appToken);

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
	public Map<String, Object> queryProduct(UUID productUC, String appToken) throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("UNIQUE_CODE", productUC);
		return dataAccessService.queryForOneRowMap("BUSS006", paramMap);
		// return dataAccessService.queryRowMapById("ES_PRODUCT", productUC);
	}

	@Override
	public void updateProduct(Map<String, Object> properties, String appToken) throws Exception {
		dataAccessService.updateSingle("ES_PRODUCT", properties);
	}

	@Override
	public void deleteProduct(UUID productUC, String appToken) throws Exception {
		dataAccessService.deleteRowById("ES_PRODUCT", productUC);
	}

	@Override
	public Map<String, Object> queryOrder(UUID orderUC, String appToken) throws Exception {
		return dataAccessService.queryRowMapById("ES_ORDER", orderUC);
	}

	@Override
	public TableMeta queryOrderItem(UUID orderUC, String appToken) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("ORDER_UC", orderUC);
		return dataAccessService.queryTableMeta("BUSS003", paramMap);
	}

	@Override
	public void discardOrder(UUID orderUC, String appToken) throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("ORDER_UC", orderUC);
		paramMap.put("STATUS", Const.ORDER_STATUS.Disable);
		dataAccessService.updateSingle("ES_ORDER", paramMap);
	}

	@Override
	public void deleteOrder(UUID orderUC, String appToken) throws Exception {
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
	public Map<String, Object> getShoppingCartInfo(String appToken) {
		UUID userUC = commonService.getUserUCByAppToken(appToken);
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("USER_UC", userUC);
		// 仅查询数量
		Long cartItemCount = dataAccessService.queryForOneObject("BUSS007", paramMap, Long.class);
		paramMap.put("CART_COUNT", cartItemCount);
		return paramMap;
	}

	@Override
	public Long addItemShoppingCart(UUID productUC, Long productCount, String appToken) {
		UUID userUC = commonService.getUserUCByAppToken(appToken);
		// 查询此种商品在cart表中数量
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("USER_UC", userUC);
		paramMap.put("PRODUCT_UC", productUC);
		BigDecimal count = dataAccessService.queryForOneObject("BUSS008", paramMap, BigDecimal.class);

		paramMap.put("UNIQUE_CODE", UUID.randomUUID());
		paramMap.put("PRODUCT_COUNT",
				count == null ? new BigDecimal(productCount) : count.add(new BigDecimal(productCount)));
		dataAccessService.update("BUSS009", paramMap);
		// 重新查询数量
		Map<String, Object> cartInfoMap = getShoppingCartInfo(appToken);
		return (Long) cartInfoMap.get("CART_COUNT");
	}

	@Override
	public TableMeta listShoppingCart(String appToken) {
		UUID userUC = commonService.getUserUCByAppToken(appToken);
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("USER_UC", userUC);

		return dataAccessService.queryTableMeta("BUSS010", paramMap);
	}

	@Override
	public Long changeItemQtyShoppingCart(UUID cartUC, Long productCount, String appToken) throws Exception {
		UUID userUC = commonService.getUserUCByAppToken(appToken);
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("UNIQUE_CODE", cartUC);
		paramMap.put("PRODUCT_COUNT", new BigDecimal(productCount));
		paramMap.put("CREATOR", userUC);
		dataAccessService.updateSingle("ES_SHOPPING_CART", paramMap);
		return productCount;
	}
}
