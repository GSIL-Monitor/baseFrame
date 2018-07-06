package com.tuniu.common.interceptor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.tuniu.common.model.ActiveUser;
import com.tuniu.common.model.OperLog;
import com.tuniu.common.model.Resource;
import com.tuniu.common.service.OperLogService;
import com.tuniu.common.service.UserService;

public class SecurityInterceptor implements HandlerInterceptor {
	@Autowired
	private UserService userService;
	
	@Autowired
	private OperLogService logService;
	
	/**
	 * controller执行前调用此方法
	 * 返回true表示继续执行，返回false中止执行
	 * 这里可以加入登录校验、权限拦截等
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	/**
	 * controller执行后但未返回视图前调用此方法
	 * 这里可在返回用户前对模型数据进行加工处理，比如这里加入公用信息以便页面显示
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		Subject subject = SecurityUtils.getSubject();//获取主体		
		HttpSession session = (HttpSession) request.getSession();
		if(null != session){
			String tokenKey =UUID.randomUUID().toString();
			String tokenValue = UUID.randomUUID().toString();
			session.setAttribute("tokenKey", tokenKey);
			session.setAttribute(tokenKey, tokenValue);
			request.setAttribute("tokenValue", tokenValue);
			
			ActiveUser user = (ActiveUser) subject.getPrincipal();//认证通过用户
			String requestUri = request.getRequestURI().replace(request.getContextPath()+"/", "");//访问路径
			@SuppressWarnings("rawtypes")
			Map argsMap = request.getParameterMap();//访问参数
			//记录操作日志
			OperLog log = new OperLog();
			log.setClientIP(request.getRemoteAddr());
			if(null !=user){
				log.setOperatorId(user.getId());
				log.setOperatorName(user.getUsername());
				List<Resource> permissions = userService.getResByUserName(user.getUsercode());
				log.setResName(getResName(requestUri,permissions));
				
			}
			log.setRequestUri(requestUri);
			log.setRequestArgs(JSON.toJSONString(argsMap));
			//过滤静态地址
			if(requestUri.indexOf(".js")==-1 && requestUri.indexOf(".css")==-1 && requestUri.indexOf(".htm")==-1
					&& requestUri.indexOf(".html")==-1 && requestUri.indexOf(".gif")==-1 && requestUri.indexOf(".png")==-1 
					&& requestUri.indexOf(".jpg")==-1 && requestUri.indexOf(".jpeg")==-1 && requestUri.indexOf(".map")==-1)
				logService.add(log);			
		}
	}
	
	//根据访问uri获取对应的资源名称
	private String getResName(String requestUri, List<Resource> permissions) {
		for(Resource res : permissions){
			if(requestUri.equals(res.getUrl())){
				return res.getName();
			}
		}
		return null;
	}

	/**
	 * controller执行后且视图返回后调用此方法
	 * 这里可得到执行controller时的异常信息
	 * 这里可记录操作日志，资源清理等
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
