package com.tuniu.common.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.common.dto.RoleDto;
import com.tuniu.common.dto.UserDto;
import com.tuniu.common.model.Role;
import com.tuniu.common.model.User;
import com.tuniu.common.service.RoleService;
import com.tuniu.common.service.UserService;
import com.tuniu.common.util.Constant;
import com.tuniu.common.util.HandlerResult;
import com.tuniu.common.util.PasswordUtil;
import com.tuniu.common.util.ValidateUtil;

@Controller
@RequestMapping("/common/user")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private RoleService roleService;
	
	//用户列表 
	@RequestMapping("/index")
	@RequiresPermissions("user:view")
	public String index(UserDto dto,Model model){
		service.loadPage(dto);
		List<Role> roleList = roleService.list(new RoleDto());
		model.addAttribute("roleList", roleList);
		model.addAttribute("dto", dto);
		
		return "common/user/authManagement";
	}
	
	//新增用户
	@RequestMapping(value="/add",method=RequestMethod.GET)
	@RequiresPermissions("user:add")
	public String toAdd(HttpServletRequest request){
		User user = new User();
		user.setLocked(Constant.NO);
		RoleDto dto = new RoleDto();
		dto.setAvailable(Constant.YES);
		List<User> pidList = service.list(new UserDto());
		List<Role> roleList = roleService.list(dto);
		request.setAttribute("user", user);
		request.setAttribute("roleList", roleList);
		request.setAttribute("pidList", pidList);
		return "common/user/userForm";
	}
	
	//新增用户
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@RequiresPermissions("user:add")
	@ResponseBody
	public HandlerResult add(@Valid User user,BindingResult result){
		HandlerResult handlerResult = new HandlerResult();
		if(result.hasErrors()){
			//如果没有通过校验
			Map<String,String> map = ValidateUtil.getErrors(result);
			handlerResult.setRetCode("failure");
			handlerResult.setRetObj(map);
			return handlerResult;
		}
		
		user.setSalt(PasswordUtil.generate(Constant.DEFAULT_SALT));
		user.setPassword(PasswordUtil.encryptMD5(Constant.DEFAULT_PASSWORD,user.getSalt(),Constant.HASH_TIMES));
		service.add(user);
		handlerResult.setRetCode("success");
		return handlerResult;
	}
	
	//更新用户
	@RequestMapping("/{id}/toUpdate")
	@RequiresPermissions("user:update")
	public String toUpdate(@PathVariable("id")Integer id,Model model){
		User user = service.get(id);
		model.addAttribute("user", user);
		RoleDto dto = new RoleDto();
		dto.setAvailable(Constant.YES);		
		List<User> pidList = service.list(new UserDto());
		List<Role> roleList = roleService.list(dto);		
		model.addAttribute("roleList", roleList);
		model.addAttribute("pidList", pidList);
		return "common/user/userForm";
	}
	
	//更新用户
	@RequestMapping("/update")
	@RequiresPermissions("user:update")
	@ResponseBody
	public String update(User user){
		service.update(user);
		return "Success";
	}
	
	//验证用户是否存在
	@RequestMapping("/checkUserExists")
	@ResponseBody
	public boolean checkUserExists(UserDto dto, HttpServletRequest request) {
		boolean isExists = false;
		List<User> list = service.list(dto);
		if (!list.isEmpty()) {
			isExists = true;
		}
		return isExists;
	}
	
	//锁定用户
	@RequestMapping("/{id}/lock")
	@RequiresPermissions("user:lock")
	@ResponseBody
	public String lock(@PathVariable("id") Integer id,HttpServletRequest request){
		User user = service.get(id);
		user.setLocked(user.getLocked()==0?1:0);
		service.update(user);
		return "Success";
	}
	
	//删除用户
	@RequestMapping("/{id}/del")
	@RequiresPermissions("user:delete")
	@ResponseBody
	public String del(@PathVariable("id") Integer id){
		User user = service.get(id);
		service.revokeRole(user);//删除关联的角色 
		service.delete(id);;
		return "Success";
	}
	
	//验证密码是否正确
	@RequestMapping("/checkPassword")
	@ResponseBody
	public boolean checkPassword(User user){		
		User u = service.get(user.getId());
		String password = u.getPassword();//数据库加密密码
		String salt = u.getSalt();
		String pwd = user.getPassword();//未加密
		String encrypt = PasswordUtil.encryptMD5(pwd, salt, Constant.HASH_TIMES);
		return password.equals(encrypt);
	}
	
	//修改密码
	@RequestMapping("/{id}/toModify")
	@RequiresPermissions("user:setPWD")
	public String toModify(@PathVariable("id")Integer id,HttpServletRequest request){
		User user = service.get(id);
		request.setAttribute("user", user);
		return "common/user/passwordForm";
	}
	
	//修改密码
	@RequestMapping("/updatePassword")
	@RequiresPermissions("user:setPWD")
	@ResponseBody
	public String updatePassword(User user){
		User u = service.get(user.getId());
		String password = user.getPassword();
		String encrypt = PasswordUtil.encryptMD5(password, u.getSalt(), Constant.HASH_TIMES);
		user.setPassword(encrypt);
		service.update(user);
		return "Success";
	}
	
	//重置密码
	@RequestMapping("/{id}/reset")
	@RequiresPermissions("user:resetPWD")
	@ResponseBody
	public String reset(@PathVariable("id") Integer id){
		User user = service.get(id);
		String password = Constant.DEFAULT_PASSWORD;
		String salt = user.getSalt();
		int times = Constant.HASH_TIMES;
		String newPassword = PasswordUtil.encryptMD5(password, salt, times);
		user.setPassword(newPassword);
		service.update(user);
		return "Success";
	}
	
	//用户详情
	@RequestMapping("/{id}/toDetail")
	@RequiresPermissions("user:detail")
	public String toDetail(@PathVariable("id")Integer id,Model model){
		User user = service.getUserDetail(id);
		model.addAttribute("user", user);
		return "common/user/userDetail";
	}

}
