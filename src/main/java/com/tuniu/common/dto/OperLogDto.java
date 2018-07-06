package com.tuniu.common.dto;

import com.tuniu.common.dto.base.BaseDto;
import com.tuniu.common.model.OperLog;

public class OperLogDto extends BaseDto<OperLog> {

	private static final long serialVersionUID = 1L;
	private String clientIP;
	private Integer operatorId;
	private String operatorName;
	private String resName;
	private String requestUri;
	private String requestArgs;

	public String getClientIP() {
		return clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public String getRequestUri() {
		return requestUri;
	}

	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}

	public String getRequestArgs() {
		return requestArgs;
	}

	public void setRequestArgs(String requestArgs) {
		this.requestArgs = requestArgs;
	}

}
