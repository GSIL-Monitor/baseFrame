package com.tuniu.business.dto;

import java.util.Date;

import com.tuniu.business.model.LeaveBill;
import com.tuniu.common.dto.base.BaseDto;
import com.tuniu.common.model.User;

public class LeaveBillDto extends BaseDto<LeaveBill> {

	private static final long serialVersionUID = 1L;
	private Integer userId;
	private User user;
	private Integer billType;
	private Integer days;
	private Date beginDate;
	private Date endDate;
	private String remark;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getBillType() {
		return billType;
	}

	public void setBillType(Integer billType) {
		this.billType = billType;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
