package com.tuniu.cms.dto;

import com.tuniu.cms.model.Article;
import com.tuniu.common.dto.base.BaseDto;

public class ArticleDto extends BaseDto<Article> {
	
	private static final long serialVersionUID = 1L;

	private Integer typeId;
	private Integer cid;
	private Integer title;
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public Integer getTitle() {
		return title;
	}
	public void setTitle(Integer title) {
		this.title = title;
	}
	
	
	
}
