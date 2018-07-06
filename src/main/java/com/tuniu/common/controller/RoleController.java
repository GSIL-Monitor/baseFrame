package com.tuniu.common.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.common.dto.ResourceDto;
import com.tuniu.common.dto.RoleDto;
import com.tuniu.common.model.Resource;
import com.tuniu.common.model.Role;
import com.tuniu.common.service.ResourceService;
import com.tuniu.common.service.RoleService;
import com.tuniu.common.util.Constant;

@Controller
@RequestMapping("/common/role")
public class RoleController {
	
	@Autowired
	private RoleService service;
	
	@Autowired
	private ResourceService resourceService;
	
	@RequestMapping(value="/add",method=RequestMethod.GET)	
	@RequiresPermissions("role:add")
	public String toAdd(HttpServletRequest request){
		Role role = new Role();
		role.setType(Constant.ROLE_EMPLOYEE);
		role.setAvailable(1);
		request.setAttribute("role", role);
		return "common/role/roleForm";
		
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@RequiresPermissions("role:add")
	@ResponseBody
	public String add(Role role, HttpServletRequest request) {
		service.add(role);
		return "Success";
	}
	
	@RequestMapping("/{id}/delete")
	@RequiresPermissions("role:delete")
	@ResponseBody
	public String delete(@PathVariable Integer id, HttpServletRequest request) {
		String info = "Success";				
		//校验角色是否关联用户, 如果关联则不允许删除
		if(service.checkRelation(id)>0){
			return "Failure";
		}
		service.delete(id);
		return info;
	}
	
	@RequestMapping("/{id}/toUpdate")
	@RequiresPermissions("role:update")
	public String toUpdate(@PathVariable Integer id, HttpServletRequest request) {
		Role role = service.get(id);
		request.setAttribute("role", role);
		return "common/role/roleForm";
	}
	
	@RequestMapping("/update")
	@RequiresPermissions("role:update")
	@ResponseBody
	public String update(Role role, HttpServletRequest request) {
		service.update(role);
		return "Success";
	}
	
	@RequestMapping("/checkRoleSame")
	@ResponseBody
	public boolean checkRoleSame(RoleDto dto, HttpServletRequest request) {
		boolean isNotSame = false;
		List<Role> list = service.list(dto);
		if (list.isEmpty()) {
			isNotSame = true;
		}
		return isNotSame;
	}
	
	@RequestMapping("/index")
	@RequiresPermissions("role:view")
	public String management(RoleDto dto, HttpServletRequest request) {
		List<Role> roleList = service.list(dto);
		request.setAttribute("roleList", roleList);
		
		return "common/role/roleManagement";
	}
	
	@RequestMapping("/{roleId}/authConfig")
	@RequiresPermissions("role:auth")
	public String authConfig(@PathVariable Integer roleId, HttpServletRequest request) {
		ResourceDto resDto = new ResourceDto();
		//获取全部资源
		List<Resource> resourceList = resourceService.list(resDto);
		request.setAttribute("resourceList", resourceList);
		
		//获取该角色所拥有的资源
		resDto.setRoleId(roleId);
		List<Resource> roleResources = resourceService.list(resDto);
		request.setAttribute("roleResources", roleResources);
		
		Role role = service.get(roleId);
		request.setAttribute("role", role);
		
		return "common/role/authConfig";
	}
	
	@RequestMapping("/{id}/saveAuth")
	@RequiresPermissions("role:auth")
	@ResponseBody
	public void saveAuth(@PathVariable Integer id, HttpServletRequest request) {
		String[] resIds = request.getParameterValues("resIds");
		service.addResources(id, resIds);
	}

}
