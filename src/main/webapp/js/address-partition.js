var Province = {
	"1" : "北京",
	"2" : "上海"
};

var City = {
	"1" : {
		"2810" : "大兴区",
		"2808" : "房山区"
	},
	"2" : {
		"2822" : "虹口区",
		"2823" : "杨浦区"
	}
};

var Country = {
	"2810" : {
		"6501" : "五环至六环之间",
		"51081" : "亦庄经济开发区"
	},
	"2808" : {
		"4194" : "四环至五环之间",
		"4205" : "六环以外"
	},
	"2822" : {
		"51530" : "大石窝镇",
		"51531" : "窦店镇"
	},
	"2823" : {
		"51528" : "城区",
		"51529" : "大安山乡"
	}

};

var Town = {
	"6501" : {
		"51534" : "韩村河镇",
		"51535" : "河北镇"
	},
	"51081" : {
		"51532" : "佛子庄乡",
		"51547" : "燕山地区",
	},
	"4194" : {
		"51546" : "阎村镇",
		"51545" : "新镇",
		"51544" : "霞云岭乡"
	},
	"4205" : {
		"51551" : "周口店镇",
		"51550" : "长阳镇",
		"51549" : "长沟镇"
	},
	"51530" : {
		"51548" : "张坊镇",
		"51539" : "蒲洼乡"
	},
	"51531" : {
		"51538" : "南窖乡",
		"51537" : "琉璃河镇"
	},
	"51528" : {
		"51536" : "良乡镇",
		"51543" : "史家营乡"
	},
	"51529" : {
		"51542" : "石楼镇",
		"51541" : "十渡镇",
		"51540" : "青龙湖镇"
	}
};

function loadProvince(provinceSelect) {
	// province 编辑地址时的定位
	var optionsHtml = "";
	$.each(Province, function(index, element) {
		optionsHtml += "<option value=" + index;

		if (index == provinceSelect) {
			optionsHtml += " selected='selected' ";
		}
		optionsHtml += " >" + element + "</option>";
	});
	$("#province-select").html(optionsHtml);
}

function loadCity(citySelect) {
	var parent = $("#province-select").val();
	var optionsHtml = "";
	$.each(City[parent], function(index, element) {

		optionsHtml += "<option value=" + index;

		if (index == citySelect) {
			optionsHtml += " selected='selected' ";
		}
		optionsHtml += " >" + element + "</option>";
	});
	$("#city-select").html(optionsHtml);
}

function loadCountry(countrySelect) {
	var parent = $("#city-select").val();
	var optionsHtml = "";
	$.each(Country[parent], function(index, element) {

		optionsHtml += "<option value=" + index;

		if (index == countrySelect) {
			optionsHtml += " selected='selected' ";
		}

		optionsHtml += " >" + element + "</option>";

	});
	$("#country-select").html(optionsHtml);
}

function loadTown(townSelect) {
	var parent = $("#country-select").val();
	var optionsHtml = "";
	$.each(Town[parent], function(index, element) {

		optionsHtml += "<option value=" + index;

		if (index == townSelect) {
			optionsHtml += " selected='selected' ";
		}

		optionsHtml += " >" + element + "</option>";

	});
	$("#town-select").html(optionsHtml);
}

function changeProvince(provinceSelect) {
	loadCity();
	loadCountry();
	loadTown();
}
function changeCity(provinceSelect) {
	loadCountry();
	loadTown();
}
function changeCountry(provinceSelect) {
	loadTown();
}

// 打印地址插入数据库的sql
function printProvinceSQL() {
	var sql = "";
	$
			.each(
					Province,
					function(index, element) {
						sql += "insert into DD_ADDRESS_PARTITION (ADD_TYPE ,PROVINCE_CODE ,CITY_CODE ,COUNTRY_CODE ,TOWN_CODE ,CODE ,DESC )";
						sql += "values('PROVINCE','" + index
								+ "',null,null,null,'" + index + "','"
								+ element + "');";
					});
	console.info(sql);
}

function printCitySQL() {
	var sql = "";
	$
			.each(
					Province,
					function(indexProvince, elementProvince) {

						$
								.each(
										City[indexProvince],
										function(index, element) {

											sql += "insert into DD_ADDRESS_PARTITION (ADD_TYPE ,PROVINCE_CODE ,CITY_CODE ,COUNTRY_CODE ,TOWN_CODE ,CODE ,DESC )";
											sql += "values('CITY','"
													+ indexProvince + "','"
													+ index + "',null,null,'"
													+ index + "','" + element
													+ "');";
										});

					});
	console.info(sql);
}

function printCountrySQL() {
	var sql = "";
	$
			.each(
					Province,
					function(indexProvince, elementProvince) {

						$
								.each(
										City[indexProvince],
										function(indexCity, elementCity) {
											$
													.each(
															Country[indexCity],
															function(index,
																	element) {

																sql += "insert into DD_ADDRESS_PARTITION (ADD_TYPE ,PROVINCE_CODE ,CITY_CODE ,COUNTRY_CODE ,TOWN_CODE ,CODE ,DESC )";
																sql += "values('COUNTRY','"
																		+ indexProvince
																		+ "','"
																		+ indexCity
																		+ "','"
																		+ index
																		+ "',null,'"
																		+ index
																		+ "','"
																		+ element
																		+ "');";
															});
										});
					});
	console.info(sql);
}

function printTownSQL() {
	var sql = "";
	$
			.each(
					Province,
					function(indexProvince, elementProvince) {

						$
								.each(
										City[indexProvince],
										function(indexCity, elementCity) {
											$
													.each(
															Country[indexCity],
															function(
																	indexCountry,
																	elementCountry) {
																$
																		.each(
																				Town[indexCountry],
																				function(
																						index,
																						element) {
																					sql += "insert into DD_ADDRESS_PARTITION (ADD_TYPE ,PROVINCE_CODE ,CITY_CODE ,COUNTRY_CODE ,TOWN_CODE ,CODE ,DESC )";
																					sql += "values('TOWN','"
																							+ indexProvince
																							+ "','"
																							+ indexCity
																							+ "','"
																							+ indexCountry
																							+ "','"
																							+ index
																							+ "','"
																							+ index
																							+ "','"
																							+ element
																							+ "');";
																				});
															});
										});
					});
	console.info(sql);
}


