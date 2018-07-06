package com.tuniu.common.dto.base;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class BaseDto<T> implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;

	/** 添加人 */
	private String addPerson;

	/** 添加时间 */
	private Date addTime;

	/** 更新人 */
	private String updatePerson;

	/** 更新时间 */
	private Date updateTime;

	/** 删除标识位，-1：全部，0：未删除，1：已删除 */
	private Integer delFlag= -1;

	/** 每页显示记录数 */
	private int pageSize = 20;

	/** 当前页码 */
	private int pageNo = 1;

	/** 总记录数 */
	private int totalRecords;

	/** 数据集 */
	private List<T> dataList;

	/** 获取分页查询开始位置 */
	public int getDataLimitStart() {
		return 0 + pageSize * (pageNo - 1);
	}

	/** 获取总页数 */
	public int getTotalPages() {
		return (totalRecords + pageSize - 1) / pageSize;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddPerson() {
		return addPerson;
	}

	public void setAddPerson(String addPerson) {
		this.addPerson = addPerson;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getUpdatePerson() {
		return updatePerson;
	}

	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

}
