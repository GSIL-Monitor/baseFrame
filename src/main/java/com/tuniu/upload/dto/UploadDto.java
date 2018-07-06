package com.tuniu.upload.dto;

import com.tuniu.common.dto.base.BaseDto;
import com.tuniu.upload.model.Upload;

public class UploadDto extends BaseDto<Upload> {

	private static final long serialVersionUID = 1L;
	private String file_name;
	private Integer typeId;
	private Integer categoryId;
	private long max_size;
	private long min_size;
	private String fileSizeWithUnit;
	private String orderName;
	private String orderType;
	private String orderSize;
	private String orderUrl;
	private String orderTime;
	
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public long getMax_size() {
		return max_size;
	}
	public void setMax_size(long max_size) {
		this.max_size = max_size;
	}
	public long getMin_size() {
		return min_size;
	}
	public void setMin_size(long min_size) {
		this.min_size = min_size;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderSize() {
		return orderSize;
	}
	public void setOrderSize(String orderSize) {
		this.orderSize = orderSize;
	}
	public String getOrderUrl() {
		return orderUrl;
	}
	public void setOrderUrl(String orderUrl) {
		this.orderUrl = orderUrl;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getFileSizeWithUnit() {
		return fileSizeWithUnit;
	}
	public void setFileSizeWithUnit(String fileSizeWithUnit) {
		this.fileSizeWithUnit = fileSizeWithUnit;
	}
	

}
