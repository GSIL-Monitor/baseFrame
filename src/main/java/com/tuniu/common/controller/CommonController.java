package com.tuniu.common.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.common.dto.OperLogDto;
import com.tuniu.common.exception.CustomException;
import com.tuniu.common.logger.CommonLogger;
import com.tuniu.common.logger.LogView;
import com.tuniu.common.model.ActiveUser;
import com.tuniu.common.model.Resource;
import com.tuniu.common.service.OperLogService;
import com.tuniu.common.service.UserService;
import com.tuniu.common.util.Constant;

@Controller
@RequestMapping("/common/main")
public class CommonController {
	
	@Autowired
	private CommonLogger logger;
	
	@Autowired
	private UserService service;
	
	@Autowired
	private OperLogService logService;
	
	@Autowired
	private LogView logView;
	
	@RequestMapping("/operLogList")
	@RequiresPermissions("log:view")
	public String log(OperLogDto dto,Model model){
		logService.loadPage(dto);
		model.addAttribute("dto", dto);
		return "common/operLogList";
	}	
	
	//控制是否开启日志实时监控
	@RequestMapping("/startLog")
	@RequiresPermissions("realLog:view")
	public String startLog(Integer flag,HttpServletRequest request) throws IOException{
		if(flag!=null && flag==Constant.NO ){						
			logView.cancel();
		}else{
			File logFile = new File(Constant.LOG_FILE);
			Subject subject = SecurityUtils.getSubject();
			ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
			Integer uid = activeUser.getId();
			subject.getSession().setAttribute("uid", uid);
			request.setAttribute("uid", uid);
			logView.realTimeShowLog(logFile,Constant.DELAY,Constant.INTERVAL,uid);
		}
		return "common/log";
	}
	
	@RequestMapping("/enable")
	@RequiresPermissions("realLog:print")
	@ResponseBody
	public String enable(Integer flag,HttpServletRequest request){
		if(flag==Constant.NO){
			logger.setEnable(false);
		}else{
			logger.setEnable(true);
		}	
		return "success";
	}
	
	
	@RequestMapping(value={"/login"})
	public String login(HttpServletRequest request) throws Exception{
		// shiro在认证过程中出现错误后将异常类名通过request返回
		String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
		if(exceptionClassName!=null){
			if(UnknownAccountException.class.getName().equals(exceptionClassName)){
				throw new CustomException("账号不存在");
			}else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)){
				throw new CustomException("用户名/密码错误");
			}else if("randomCodeError".equals(exceptionClassName)){
				throw new CustomException("验证码错误");
			}else if(DisabledAccountException.class.getName().equals(exceptionClassName)){
				throw new CustomException("此账号已被锁定！！！");
			}else{
				throw new CustomException("未知错误");
			}
		}				
		return "common/login";
	}
	
	@RequestMapping("/refuse")
	public String refuse(){
		return "refuse";
	}
	
	@RequestMapping("/index")
	public String index(Model model){
		Subject subject = SecurityUtils.getSubject();
		ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
		model.addAttribute("activeUser", activeUser);
		
		List<Resource> resources = service.getResByUserName(activeUser.getUsercode());
		List<Resource> menus = new ArrayList<Resource>();
		for(Resource res : resources){
			if("menu".equals(res.getType())){
				menus.add(res);
			}
		}
		model.addAttribute("menus",menus);
		return "common/index";
	}

}
