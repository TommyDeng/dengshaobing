package com.tom.dengshaobing.common.bo.sys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListForm {
	private List<Map<String, Object>> dataList;

	public List<Map<String, Object>> getDataList() {
		return dataList;
	}

	public void setDataList(List<Map<String, Object>> dataList) {
		this.dataList = dataList;
	}

	private List<String> checkedList;

	public List<String> getCheckedList() {
		return checkedList;
	}

	public void setCheckedList(List<String> checkedList) {
		this.checkedList = checkedList;
	}

}