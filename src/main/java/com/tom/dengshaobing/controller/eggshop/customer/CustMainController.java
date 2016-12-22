package com.tom.dengshaobing.controller.eggshop.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tom.dengshaobing.common.Const;
import com.tom.dengshaobing.common.bo.sys.ListForm;
import com.tom.dengshaobing.common.bo.sys.MapForm;
import com.tom.dengshaobing.controller.BaseController;
import com.tom.dengshaobing.service.DataAccessService;
import com.tom.dengshaobing.service.eggshop.EggShopBussService;
import com.tom.utils.JsonParseUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月26日 上午10:19:18
 *
 */

@Slf4j
@Controller
@RequestMapping("/eggshop/customer")
public class CustMainController extends BaseController {
	public static final String BasePath = "/eggshop/customer/";

	@Autowired
	EggShopBussService bussService;

	@Autowired
	DataAccessService dataAccessService;

	@Autowired
	private Environment env;
	
	@RequestMapping("/main")
	public String main(@RequestParam(name = "visitId", required = false) String visitId,
			@RequestParam(name = "visitType", required = false) String visitType, ModelMap map, String AT)
			throws Exception {
		AT = pageInit(AT, visitId, visitType, map);
		map.put("swiperList", dataAccessService.queryMapList("ES_BUSS001"));
		map.put("hotList", dataAccessService.queryMapList("ES_BUSS002"));
		map.put("recentList", dataAccessService.queryMapList("ES_BUSS003"));

		map.put("previousPage", "main");

		return BasePath + "main";
	}

	@RequestMapping("/category")
	public String category(@RequestParam(name = "visitId", required = false) String visitId,
			@RequestParam(name = "visitType", required = false) String visitType, ModelMap map, String AT,
			String selectCategory) throws Exception {
		AT = pageInit(AT, visitId, visitType, map);

		if (StringUtils.isEmpty(selectCategory)) {
			selectCategory = null;
		}

		map.put("categoryList", dataAccessService.queryMapList("ES_BUSS004"));

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("CATEGORY", selectCategory);
		map.put("productList", dataAccessService.queryMapList("ES_BUSS005", paramMap));

		// selected category
		map.put("selectCategory", selectCategory);

		// to detail page param
		map.put("previousPage", "category");
		return BasePath + "category";
	}

	@RequestMapping("/itemdetail")
	public String itemdetail(@RequestParam(name = "visitId", required = false) String visitId,
			@RequestParam(name = "visitType", required = false) String visitType, ModelMap map, String AT,
			String productUC, String previousPage, String previousCategory) throws Exception {
		AT = pageInit(AT, visitId, visitType, map);

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("UNIQUE_CODE", productUC);
		Map<String, Object> product = dataAccessService.queryForOneRow("ES_BUSS006", paramMap);

		map.put("product", product);

		Map<String, Object> cartInfo = bussService.getShoppingCartInfo(AT);
		map.put("cartInfo", cartInfo);

		Map<String, Object> mediaParamMap = new HashMap<>();
		mediaParamMap.put("PRODUCT_UC", productUC);
		map.put("productMediaList", dataAccessService.queryMapList("ES_BUSS023", mediaParamMap));
		// 添加按钮
		map.put("productUC", productUC);

		// 返回按钮
		map.put("previousPage", previousPage);
		map.put("previousCategory", previousCategory);

		return BasePath + "itemdetail";
	}

	@RequestMapping("/addItem")
	@ResponseBody
	public String addItem(ModelMap map, String productUC, Long productCount, String AT) throws Exception {
		Long shoppingCartCount = bussService.addItemShoppingCart(UUID.fromString(productUC), productCount, AT);
		return String.valueOf(shoppingCartCount);
	}

	@RequestMapping("/cart")
	public String cart(@RequestParam(name = "visitId", required = false) String visitId,
			@RequestParam(name = "visitType", required = false) String visitType, ModelMap map, String AT)
			throws Exception {
		AT = pageInit(AT, visitId, visitType, map);

		cartBlockDataLoad(map, AT);

		map.put("previousPage", "cart");

		return BasePath + "cart";
	}

	@RequestMapping("/changeCartItemQty")
	public String changeCartItemQty(ModelMap map, String cartItemUC, String itemCount, String AT) throws Exception {
		bussService.changeCartItemQty(UUID.fromString(cartItemUC), Long.parseLong(itemCount), AT);

		cartBlockDataLoad(map, AT);

		return BasePath + "cart :: #cart-block";
	}

	@RequestMapping("/deleteCartItem")
	public String deleteCartItem(ModelMap map, String cartItemUC, String AT) throws Exception {
		bussService.deleteCartItem(UUID.fromString(cartItemUC), AT);

		cartBlockDataLoad(map, AT);

		return BasePath + "cart :: #cart-block";
	}

	@RequestMapping("/selectCartItem")
	public String selectCartItem(ModelMap map, String cartItemUC, String AT) throws Exception {
		bussService.selectCartItem(UUID.fromString(cartItemUC), AT);

		cartBlockDataLoad(map, AT);

		return BasePath + "cart :: #cart-block";
	}

	@RequestMapping("/selectAllCartItem")
	public String selectAllCartItem(ModelMap map, String AT, boolean selected) throws Exception {
		bussService.selectAllCartItem(AT, selected);

		cartBlockDataLoad(map, AT);

		return BasePath + "cart :: #cart-block";
	}

	private void cartBlockDataLoad(ModelMap map, String AT) {
		ListForm listForm = new ListForm();
		listForm.setDataList(bussService.listShoppingCart(AT));
		// 全选状态
		boolean cartSelectAllStatus = false;
		// 总金额
		Double cartTotalAmount = 0d;
		// 选择check列表
		List<Object> checkedList = new ArrayList<>();
		for (Map<String, Object> record : listForm.getDataList()) {
			if ((boolean) record.get("SELECTED")) {
				checkedList.add(record.get("UNIQUE_CODE"));
				cartTotalAmount += (Double) record.get("SUB_AMT");
			} else {
				// 只要有未选择的项目，则设置为可全选
				cartSelectAllStatus = true;
			}
		}

		listForm.setCheckedList(checkedList);

		map.put("cartList", listForm);
		map.put("cartSelectAllStatus", cartSelectAllStatus);
		map.put("cartTotalAmount", cartTotalAmount);

		// preorder按钮上的AT刷新
		map.put(PxAT, AT);

	}

	@RequestMapping("/preorder")
	public String preorder(@RequestParam(name = "visitId", required = false) String visitId,
			@RequestParam(name = "visitType", required = false) String visitType, ModelMap map, String AT)
			throws Exception {
		AT = pageInit(AT, visitId, visitType, map);

		ListForm listForm = new ListForm();
		// address list
		List<Map<String, Object>> addressList = bussService.getUserDeliveryAddressList(AT);
		listForm.setDataList(addressList);
		// 默认选中第一个
		if (!CollectionUtils.isEmpty(addressList)) {
			List<Object> checkedList = new ArrayList<>();
			checkedList.add(addressList.get(0).get("UNIQUE_CODE"));
			listForm.setCheckedList(checkedList);
		}

		// selected item list
		List<Map<String, Object>> selectedItemList = bussService.getSelectedItemList(AT);

		map.put("addressList", listForm);

		map.put("selectedItemList", selectedItemList);

		return BasePath + "preorder";
	}

	@RequestMapping("/submitorder")
	public String submitorder(@RequestParam(name = "visitId", required = false) String visitId,
			@RequestParam(name = "visitType", required = false) String visitType, ModelMap map, String AT,
			ListForm listForm, HttpServletRequest request) throws Exception {
		AT = pageInit(AT, visitId, visitType, map);

		String ipAddress = request.getHeader("X-Real-IP");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}

		UUID selectedAddressUC = UUID.fromString((String) listForm.getCheckedList().get(0));

		String paymentType = Const.PAYMENT_TYPE.Weixin;

		UUID orderUC = bussService.submitOrder(AT, selectedAddressUC, paymentType, ipAddress);

		map.put("orderUC", orderUC.toString());
		return "redirect:" + BasePath + "successorder";
	}

	@RequestMapping("/successorder")
	public String successorder(@RequestParam(name = "visitId", required = false) String visitId,
			@RequestParam(name = "visitType", required = false) String visitType, ModelMap map, String AT,
			String orderUC) throws Exception {
		AT = pageInit(AT, visitId, visitType, map);

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("UNIQUE_CODE", UUID.fromString(orderUC));
		Map<String, Object> resultMap = dataAccessService.queryForOneRow("ES_BUSS025", paramMap);

		JsonParseUtils.generateJsonString(resultMap);

		map.put("appId", env.getProperty("WeixinPlatform.AppID"));
		map.put("timeStamp", System.currentTimeMillis());
		map.put("nonceStr", UUID.randomUUID().toString().replaceAll("-", ""));
		
		"package" : "prepay_id=u802345jgfjsdfgsdg888",
		"signType" : "MD5", // 微信签名方式:
		"paySign" : "70EA570631E4BB79628FBCA90534C63FF7FADD89" // 微信签名
			
			

		return BasePath + "successorder";
	}

	@RequestMapping("/myprofile")
	public String myprofile(@RequestParam(name = "visitId", required = false) String visitId,
			@RequestParam(name = "visitType", required = false) String visitType, ModelMap map, String AT)
			throws Exception {
		AT = pageInit(AT, visitId, visitType, map);

		map.put("userInfo", bussService.getWeixinUserInfo(AT));
		map.put("weixinUserInfo", bussService.getWeixinUserInfoDetail(AT));
		map.put("contactInfo", bussService.getUserInfo(AT));
		return BasePath + "myprofile";
	}

	@RequestMapping("/saveUserContactInfo")
	@ResponseBody
	public String saveUserContactInfo(ModelMap map, String mobile, String email, String AT) throws Exception {
		Map<String, Object> userInfo = new HashMap<>();
		userInfo.put("MOBILE", mobile);
		userInfo.put("EMAIL", email);
		bussService.saveUserInfo(userInfo, AT);
		return "";
	}

	@RequestMapping("/myorder")
	public String myorder(@RequestParam(name = "visitId", required = false) String visitId,
			@RequestParam(name = "visitType", required = false) String visitType, ModelMap map, String AT,
			String orderStatus) throws Exception {
		AT = pageInit(AT, visitId, visitType, map);

		List<Map<String, Object>> orderList = bussService.getOrderList(AT, orderStatus);

		map.put("orderList", orderList);

		map.put("orderStatus", orderStatus);

		map.put("previousPage", "myprofile");

		return BasePath + "myorder";
	}

	@RequestMapping("/address")
	public String address(@RequestParam(name = "visitId", required = false) String visitId,
			@RequestParam(name = "visitType", required = false) String visitType, ModelMap map, String AT)
			throws Exception {
		AT = pageInit(AT, visitId, visitType, map);
		List<Map<String, Object>> addressList = bussService.getUserDeliveryAddressList(AT);
		map.put("addressList", addressList);

		map.put("previousPage", "myprofile");

		return BasePath + "address";
	}

	@RequestMapping("/addressEdit")
	public String addressEdit(@RequestParam(name = "visitId", required = false) String visitId,
			@RequestParam(name = "visitType", required = false) String visitType, ModelMap map, String AT, String rowUC,
			String previousPage) throws Exception {
		AT = pageInit(AT, visitId, visitType, map);

		MapForm mapForm = new MapForm();
		if (rowUC != null) {
			Map<String, Object> address = dataAccessService.queryForOneRowAllColumn("ES_DELIVERY_ADDRESS",
					UUID.fromString(rowUC));
			mapForm.setProperties(address);
		}

		map.put(SxFormData, mapForm);

		map.put("previousPage", previousPage);

		return BasePath + "addressEdit";
	}

	@RequestMapping("/addressSave")
	public String addressSave(@RequestParam(name = "visitId", required = false) String visitId,
			@RequestParam(name = "visitType", required = false) String visitType, ModelMap map, String rowUC, String AT,
			@ModelAttribute MapForm mapForm, String previousPage) throws Exception {
		AT = pageInit(AT, visitId, visitType, map);
		if (StringUtils.isEmpty(rowUC)) {
			mapForm.getProperties().put("UNIQUE_CODE", UUID.randomUUID());
			mapForm.getProperties().put("USER_UC", commonService.getUserUCByAppToken(AT));
			dataAccessService.insertSingle("ES_DELIVERY_ADDRESS", mapForm.getProperties());
		} else {
			dataAccessService.updateSingle("ES_DELIVERY_ADDRESS", mapForm.getProperties());
		}
		return "redirect:" + BasePath + (StringUtils.isBlank(previousPage) ? "address" : previousPage);
	}

	@RequestMapping("/addressDelete")
	public String addressDelete(@RequestParam(name = "visitId", required = false) String visitId,
			@RequestParam(name = "visitType", required = false) String visitType, ModelMap map, String rowUC, String AT)
			throws Exception {
		AT = pageInit(AT, visitId, visitType, map);
		dataAccessService.deleteRowById("ES_DELIVERY_ADDRESS", UUID.fromString(rowUC));

		return "redirect:" + BasePath + "address";
	}

}
