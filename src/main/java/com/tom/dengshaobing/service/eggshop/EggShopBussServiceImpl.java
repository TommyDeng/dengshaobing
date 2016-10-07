package com.tom.dengshaobing.service.eggshop;

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
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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
		insertParamMap.put("CREATOR", String.valueOf(userUC));
		dataAccessService.insertSingle("TX_PRODUCT", insertParamMap);

		insertParamMap.put("REMARK", properties.get("REMARK"));
		dataAccessService.insertSingle("TX_PRODUCT_DETAIL", insertParamMap);

	}

	@Override
	public Map<String, Object> queryProduct(UUID productUC, String appToken) throws Exception {
		return dataAccessService.queryRowMapById("TX_PRODUCT", productUC);
	}

	@Override
	public Map<String, Object> queryProductDetail(UUID productUC, String appToken) throws Exception {
		return dataAccessService.queryRowMapById("TX_PRODUCT_DETAIL", productUC);
	}

	@Override
	public void updateProduct(Map<String, Object> properties, String appToken) throws Exception {
		dataAccessService.updateSingle("TX_PRODUCT", properties);
	}

	@Override
	public void updateProductDetail(Map<String, Object> properties, String appToken) throws Exception {
		dataAccessService.updateSingle("TX_PRODUCT_DETAIL", properties);
	}

	@Override
	public void deleteProduct(UUID productUC, String appToken) throws Exception {
		dataAccessService.deleteRowById("TX_PRODUCT", productUC);
		dataAccessService.deleteRowById("TX_PRODUCT_DETAIL", productUC);
	}

	@Override
	public Map<String, Object> queryOrder(UUID orderUC, String appToken) throws Exception {
		return dataAccessService.queryRowMapById("TX_ORDER", orderUC);
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
		dataAccessService.updateSingle("TX_ORDER", paramMap);
	}

	@Override
	public void deleteOrder(UUID orderUC, String appToken) throws Exception {
		dataAccessService.deleteRowById("TX_ORDER", orderUC);
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("ORDER_UC", orderUC);
		dataAccessService.update("BUSS005", paramMap);
	}

	@Override
	public List<Map<String, Object>> listAllProductForMain() {
		return dataAccessService.queryMapList("BUSS006", null);
	}

	@Override
	public Map<String, Object> getShoppingCartInfo(String appToken) {
		UUID userUC = commonService.getUserUCByAppToken(appToken);
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("USER_UC", userUC);
		// 仅查询数量
		long cartItemCount = dataAccessService.queryForOneObject("BUSS007", paramMap, Long.class);
		paramMap.put("CART_COUNT", cartItemCount);
		return paramMap;
	}
}
