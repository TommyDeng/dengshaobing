package com.tom.utils;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.tom.dengshaobing.common.bo.wmp.xml.weixinpayment.UnifiedOrderRequestXml;

/**
 * 
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月13日 下午4:25:46
 *
 */
public class PaymentSignUtils {

	public static void sign(UnifiedOrderRequestXml request, String signkey) throws Exception {
		// 生成Map
		Map<String, String> map = BeanUtils.describe(request);
		// key排序
		Object[] sortedKeys = map.keySet().toArray();
		Arrays.sort(sortedKeys);

		// 拼接参数字符串
		StringBuilder strBuilder = new StringBuilder();
		for (Object key : sortedKeys) {
			String value = map.get(key);

			// 签名需要排除空值
			if ("class".equals(key) || StringUtils.isBlank(value)) {
				continue;
			}
			strBuilder.append(key).append("=").append(value).append("&");
		}
		strBuilder.append("key=").append(signkey);

		// 生成签名 默认使用MD5加密
		String signResult = DigestUtils.md5Hex(strBuilder.toString()).toUpperCase();

		//写入到sign字段
		request.setSign(signResult);
	}

}
