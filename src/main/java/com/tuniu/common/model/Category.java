package com.tuniu.common.model;

public class Category extends Resource {

	private static final long serialVersionUID = 1L;
	private Integer cid;
	private BaseData category;
	private String icon;
	private Integer parentid;
	private String parentids;
	public BaseData getCategory() {
		return category;
	}
	public void setCategory(BaseData category) {
		this.category = category;
	}
	
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Integer getParentid() {
		return parentid;
	}
	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}
	public String getParentids() {
		return parentids;
	}
	public void setParentids(String parentids) {
		this.parentids = parentids;
	}
	
	

}
