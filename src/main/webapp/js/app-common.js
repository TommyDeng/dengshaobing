/**
 * 验证提交form
 * 
 * @returns
 */
function formValidation() {
	var validateSuccess = true;

	// 1.Safari等浏览器不支持html5的input标签required属性
	$("form [required]").each(function(index, element) {
		if ($(element).val() == null || $(element).val().length == 0) {
			$(element).addClass("input-required");
			validateSuccess = false;
		} else {
			// 有值时消除提示
			$(element).removeClass("input-required");
		}

		// 当输入时消除提示
		$(element).on('input', function() {
			$(this).removeClass("input-required");
		});
		// 当值变更时消除提示(适用于文件)
		$(element).on('change', function() {
			$(this).removeClass("input-required");
		});
	});

	return validateSuccess;
}

/**
 * LocalStorage 操作
 */
// 以“key”为名称存储一个值“value”
function LocalStorage_set(key, value) {
	localStorage.setItem(key, value);
}

// 获取名称为“key”的值
function LocalStorage_get(key) {
	return localStorage.getItem(key);
}

// 枚举localStorage的方法：
function LocalStorage_printAll() {
	console.info('localStorage print');
	for (var i = 0; i < localStorage.length; i++) {
		var key = localStorage.key(i);
		var value = localStorage.key(i);
		var value = localStorage.getItem(key);
		console.info(key + ':' + value);
	}
}
// 删除名称为“key”的信息。
function LocalStorage_removeItem(key) {
	localStorage.removeItem(key);
}

// 清空localStorage中所有信息
function LocalStorage_clear() {
	localStorage.clear();
}