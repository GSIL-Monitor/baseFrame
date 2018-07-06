package com.tuniu.upload.dto;

import com.tuniu.common.model.BaseData;
import com.tuniu.common.model.Category;

public class AccordionDto {

	private BaseData fileType;
	private Category category;

	public BaseData getFileType() {
		return fileType;
	}

	public void setFileType(BaseData fileType) {
		this.fileType = fileType;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
