package com.tom.dengshaobing.common.bo.sys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListForm {
	private List<Map<String, Object>> dataList = new ArrayList<>();

	public List<Map<String, Object>> getDataList() {
		return dataList;
	}

	public void setDataList(List<Map<String, Object>> dataList) {
		this.dataList = dataList;
	}

	private List<Object> checkedList = new ArrayList<>();

	public List<Object> getCheckedList() {
		return checkedList;
	}

	public void setCheckedList(List<Object> checkedList) {
		this.checkedList = checkedList;
	}


}