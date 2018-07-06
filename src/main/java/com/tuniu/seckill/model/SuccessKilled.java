package com.tuniu.seckill.model;

import java.util.Date;

public class SuccessKilled {
	private int seckillId;
	private long userphone;
	private int state;
	private Date createtime;
	private SecKill seckill;
	public int getSeckillId() {
		return seckillId;
	}
	public void setSeckillId(int seckillId) {
		this.seckillId = seckillId;
	}
	public long getUserphone() {
		return userphone;
	}
	public void setUserphone(long userphone) {
		this.userphone = userphone;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public SecKill getSeckill() {
		return seckill;
	}
	public void setSeckill(SecKill seckill) {
		this.seckill = seckill;
	}
	@Override
	public String toString() {
		return "SuccessKilled [seckillId=" + seckillId + ", userphone=" + userphone + ", state=" + state
				+ ", createtime=" + createtime + ", seckill=" + seckill + "]";
	}
	

}
