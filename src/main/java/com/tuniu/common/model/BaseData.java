package com.tuniu.common.model;

import java.util.List;

import com.tuniu.common.model.base.BaseModel;


public class BaseData extends BaseModel {

	private static final long serialVersionUID = 1L;

	private String key;
	private String value;
	private Integer seq;
	private String url;
	private List<String> keyList;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}


	public BaseData(String key, String value) {
		this.key = key;
		this.value = value;
	}
	

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<String> getKeyList() {
		return keyList;
	}

	public void setKeyList(List<String> keyList) {
		this.keyList = keyList;
	}

	public BaseData() {
	}

	
	public BaseData(String key, String value, Integer seq) {
		this.key = key;
		this.value = value;
		this.seq = seq;
	}
	
	@Override
	public String toString() {
		return "BaseData [key=" + key + ", value=" + value + ", seq=" + seq
				+ "]";
	}

}
