package com.tuniu.seckill.dto;

import com.tuniu.seckill.enums.SeckillStateEnum;
import com.tuniu.seckill.model.SuccessKilled;

public class SecKillExecution {

	private long seckillId;
	private int state;
	private String stateInfo;
	private SuccessKilled successKilled;

	public SecKillExecution(long seckillId, SeckillStateEnum enums, SuccessKilled successKilled) {
		super();
		this.seckillId = seckillId;
		this.state = enums.getState();
		this.stateInfo = enums.getStateInfo();
		this.successKilled = successKilled;
	}

	public SecKillExecution(long seckillId, SeckillStateEnum enums) {
		super();
		this.seckillId = seckillId;
		this.state = enums.getState();
		this.stateInfo = enums.getStateInfo();
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public SuccessKilled getSuccessKilled() {
		return successKilled;
	}

	public void setSuccessKilled(SuccessKilled successKilled) {
		this.successKilled = successKilled;
	}

	@Override
	public String toString() {
		return "SecKillExecution [seckillId=" + seckillId + ", state=" + state + ", stateInfo=" + stateInfo
				+ ", successKilled=" + successKilled + "]";
	}

}
