package com.tuniu.business.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.business.dto.LeaveBillDto;
import com.tuniu.business.model.LeaveBill;
import com.tuniu.business.service.LeaveBillService;
import com.tuniu.common.dto.BaseDataDto;
import com.tuniu.common.model.ActiveUser;
import com.tuniu.common.model.BaseData;
import com.tuniu.common.service.BaseDataService;
import com.tuniu.common.util.Constant;

@Controller
@RequestMapping("/leaveBill")
public class LeaveBillController {
	
	@Autowired
	private LeaveBillService service;
	
	@Autowired
	private BaseDataService baseDataService;
	
	@ModelAttribute("leaveTypeList")
	public List<BaseData> getLeaveTypeList(){
		BaseDataDto dto = new BaseDataDto();
		dto.setKey(Constant.LEAVE_TYPE);
		dto.setDelFlag(Constant.NO);
		return baseDataService.list(dto);
	}
	
	private ActiveUser getActiveUser(){
		return (ActiveUser) SecurityUtils.getSubject().getPrincipal();
	}
	
	//relID : 联动tabs的ID,作用：点击申请请假后，联动打开'我的业务'页签，
	@RequestMapping("/index/{relID}")
	public String index(@PathVariable("relID")Integer relID,LeaveBillDto dto ,HttpServletRequest request){		
		dto.setUserId(getActiveUser().getId());
		service.loadPage(dto);
		request.setAttribute("dto", dto);
		request.setAttribute("relID", relID);
		return "business/leaveBill/index";
	}
	
	@RequestMapping(value="/add/{relID}",method=RequestMethod.GET)
	public String toAdd(@PathVariable("relID")Integer relID,@ModelAttribute("leaveBill")LeaveBill leaveBill,HttpServletRequest request){
		request.setAttribute("relID", relID);
		return "business/leaveBill/form";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public String add(LeaveBill leaveBill){
		leaveBill.setUserId(getActiveUser().getId());
		service.add(leaveBill);
		return "Success";
	}
	
	@RequestMapping("/{id}/update/{relID}")
	public String update(@PathVariable("id")Integer id,@PathVariable("relID")Integer relID,HttpServletRequest request){
		LeaveBill leaveBill = service.get(id);
		request.setAttribute("leaveBill", leaveBill);
		request.setAttribute("relID", relID);
		return "business/leaveBill/form";
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public String update(LeaveBill leaveBill){
		service.update(leaveBill);
		return "Success";
	}
	
	@RequestMapping("/{id}/delete")
	@ResponseBody
	public String delete(@PathVariable("id")Integer id){
		service.delete(id);
		return "Success";
	}

}
