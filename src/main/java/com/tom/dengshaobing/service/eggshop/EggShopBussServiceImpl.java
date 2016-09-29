package com.tom.dengshaobing.service.eggshop;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
	public UUID getUserUCByOpenid(String openid) {
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
			namedParameterJdbcTemplate.update(SqlStatements.get("BUSS003"), insertParamMap);

			// 重新查询
			userUC = dataAccessService.queryForOneObject(SqlStatements.get("BUSS001"), paramMap, UUID.class);
		}
		return userUC;
	}

	@Override
	public TableMeta listAllProduct() {
		return dataAccessService.queryTableMetaBySql(SqlStatements.get("BUSS004"), null);
	}

	@Override
	public void addProduct(Map<String, Object> properties, UUID userUC) {
		String productUC = UUID.randomUUID().toString();
		Map<String, Object> insertParamMap = new HashMap<>();
		insertParamMap.put("UNIQUE_CODE", productUC);
		insertParamMap.put("NAME", properties.get("name"));
		insertParamMap.put("PRICE", properties.get("price"));
		insertParamMap.put("CREATOR", userUC.toString());

		namedParameterJdbcTemplate.update(SqlStatements.get("BUSS005"), insertParamMap);

		insertParamMap.put("UNIQUE_CODE", UUID.randomUUID().toString());
		insertParamMap.put("PRODUCT_UC", productUC);
		insertParamMap.put("REMARK", properties.get("remark"));
		namedParameterJdbcTemplate.update(SqlStatements.get("BUSS006"), insertParamMap);

	}

}
