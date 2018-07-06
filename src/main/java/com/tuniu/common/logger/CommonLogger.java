package com.tuniu.common.logger;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class CommonLogger {
	
	private final static Logger logger = LoggerFactory.getLogger(CommonLogger.class);
	private long beginTime;
	private long endTime;
	private boolean enable = true;
	
	public void logBef(JoinPoint point) {
		beginTime = System.currentTimeMillis();
		logger.info("是否开启日志:" + enable);
		// 可添加日志开关
		if(enable){
			String className = point.getTarget().getClass().getName(); // 拦截的实体类名称
			String methodName = point.getSignature().getName(); // 拦截的方法名称
			Object[] args = point.getArgs(); // 拦截的方法参数
	        logger.info(className + "." + methodName + "【Bgn】, requestArgs：" + JSON.toJSONString(args));
		}
	}
	
	public void logAft(JoinPoint point, Object returnVal) {
		if(enable){
		endTime = System.currentTimeMillis();
		String className = point.getTarget().getClass().getName(); // 拦截的实体类名称
		String methodName = point.getSignature().getName(); // 拦截的方法名称
        logger.info("【耗时】," +(endTime-beginTime) + "【类.方法】," + className + "." + methodName + "【End】, returnValue：" + JSON.toJSONString(returnVal));
		}
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}	

}
