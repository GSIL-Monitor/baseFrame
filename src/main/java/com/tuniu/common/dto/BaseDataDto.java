package com.tuniu.common.dto;

import java.util.List;

import com.tuniu.common.dto.base.BaseDto;
import com.tuniu.common.model.BaseData;

public class BaseDataDto extends BaseDto<BaseData> {

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

	public List<String> getKeyList() {
		return keyList;
	}

	public void setKeyList(List<String> keyList) {
		this.keyList = keyList;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
}
