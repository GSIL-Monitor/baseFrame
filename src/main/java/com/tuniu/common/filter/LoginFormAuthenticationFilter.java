package com.tuniu.common.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

public class LoginFormAuthenticationFilter extends FormAuthenticationFilter {

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token,
			Subject subject, ServletRequest request, ServletResponse response)
			throws Exception {
		WebUtils.getAndClearSavedRequest(request);//清空上一次请求的访问地址
		WebUtils.redirectToSavedRequest(request, response, getSuccessUrl());
		
		//记住上次访问地址
		/*SavedRequest savedRequest = WebUtils.getSavedRequest(request);
		if(savedRequest!=null){
			System.out.println(" * * * * * * * * * "+savedRequest.getRequestUrl());
			WebUtils.redirectToSavedRequest(request, response, savedRequest.getRequestUrl());
		}else
			WebUtils.redirectToSavedRequest(request, response, getSuccessUrl());*/
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		HttpSession session = ((HttpServletRequest)request).getSession();
		String randomcode = request.getParameter("randomcode");
		String validateCode = (String)session.getAttribute("validateCode");
		if(validateCode!=null && randomcode!=null){
			if(!validateCode.equals(randomcode)){
				//randomCodeError表示验证码错误
				request.setAttribute("shiroLoginFailure", "randomCodeError");
				//拒绝访问，不再校验账号和密码
				return true;
			}
		}
		return super.onAccessDenied(request, response, mappedValue);
	}
	
	

	
}
