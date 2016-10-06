//以“key”为名称存储一个值“value”
function setLocalStorage(key, value) {
	localStorage.setItem("key", "value");
}

// 获取名称为“key”的值
function getLocalStorage(key) {
	return localStorage.getItem("key");
}

// 枚举localStorage的方法：
function printAllLocalStorage() {
	console.info('localStorage print');
	for (var i = 0; i < localStorage.length; i++) {
		var key = localStorage.key(i);
		var value = localStorage.key(i);
		var value = localStorage.getItem(key);
		console.info(key + ':' + value);
	}
}
// 删除名称为“key”的信息。
function removeLocalStorageItem(key) {
	localStorage.removeItem(key);
}

// 清空localStorage中所有信息
function clearLocalStorage() {
	localStorage.clear();
}