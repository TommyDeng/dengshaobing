package com.tom.dengshaobing.service.eggshop;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.tom.dengshaobing.common.Const;
import com.tom.dengshaobing.common.bo.sys.TableMeta;
import com.tom.dengshaobing.service.DataAccessService;
import com.tom.dengshaobing.sqlstatements.SqlStatements;

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

	@Override
	public TableMeta listOrderByUserUC(UUID userUC) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("USER_ID", userUC);

		return dataAccessService.queryTableMetaBySql(SqlStatements.get("BUSS002"), paramMap);
	}

	@Override
	public TableMeta listAllProduct() {
		return dataAccessService.queryTableMetaBySql(SqlStatements.get("BUSS004"), null);
	}

	@Override
	public void addProduct(Map<String, Object> properties, UUID userUC) throws Exception {
		if (properties == null)
			return;
		String creator = userUC == null ? null : userUC.toString();

		String productUC = UUID.randomUUID().toString();
		Map<String, Object> insertParamMap = new HashMap<>();
		insertParamMap.put("UNIQUE_CODE", productUC);
		insertParamMap.put("NAME", properties.get("name"));
		insertParamMap.put("PRICE", properties.get("price"));
		insertParamMap.put("CREATOR", creator);
		dataAccessService.insertSingle("TX_PRODUCT", insertParamMap);

		insertParamMap.put("UNIQUE_CODE", productUC);
		insertParamMap.put("REMARK", properties.get("remark"));
		dataAccessService.insertSingle("TX_PRODUCT_DETAIL", insertParamMap);

	}

	@Override
	public Map<String, Object> queryProduct(UUID productUC, UUID userUC) throws Exception {
		return dataAccessService.queryRowMapById("TX_PRODUCT", productUC);
	}

	@Override
	public Map<String, Object> queryProductDetail(UUID productUC, UUID userUC) throws Exception {
		return dataAccessService.queryRowMapById("TX_PRODUCT_DETAIL", productUC);
	}

	@Override
	public void updateProduct(Map<String, Object> properties, UUID userUC) throws Exception {
		dataAccessService.updateSingle("TX_PRODUCT", properties);
	}

	@Override
	public void updateProductDetail(Map<String, Object> properties, UUID userUC) throws Exception {
		dataAccessService.updateSingle("TX_PRODUCT_DETAIL", properties);
	}

	@Override
	public void deleteProduct(UUID productUC, UUID userUC) {
		// TODO Auto-generated method stub

	}

	@Override
	public UUID getUserUCByOpenid(String openid) throws Exception {
		if (StringUtils.isBlank(openid)) {
			return null;
		}
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("OPENID", openid);
		UUID userUC = null;

		userUC = dataAccessService.queryForOneObject(SqlStatements.get("BUSS001"), paramMap, UUID.class);
		// 无用户直接绑定
		if (userUC == null) {
			Map<String, Object> insertParamMap = new HashMap<>();
			insertParamMap.put("UNIQUE_CODE", UUID.randomUUID().toString());
			insertParamMap.put("OPENID", openid);
			insertParamMap.put("STATUS", Const.USER_STATUS.Active);
			insertParamMap.put("TYPE", Const.USER_TYPE.Weixin);
			dataAccessService.insertSingle("TX_USER", insertParamMap);

			// 重新查询
			userUC = dataAccessService.queryForOneObject(SqlStatements.get("BUSS001"), paramMap, UUID.class);
		}
		return userUC;
	}

}
