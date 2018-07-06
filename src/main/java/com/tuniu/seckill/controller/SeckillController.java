package com.tuniu.seckill.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.common.util.Constant;
import com.tuniu.common.util.HandlerResult;
import com.tuniu.seckill.dto.Exposer;
import com.tuniu.seckill.dto.SecKillExecution;
import com.tuniu.seckill.enums.SeckillStateEnum;
import com.tuniu.seckill.exception.RepeatKillException;
import com.tuniu.seckill.exception.SeckillCloseException;
import com.tuniu.seckill.exception.SeckillException;
import com.tuniu.seckill.model.SecKill;
import com.tuniu.seckill.service.SeckillService;

@Controller
@RequestMapping("/seckill")
public class SeckillController {

	@Autowired
	private SeckillService service;
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String getList(Model model){
		List<SecKill> seckillList = service.getSeckillList();
		model.addAttribute("list", seckillList);
		return "seckill/list";
	}
	
	@RequestMapping(value="/{seckillId}/detail",method=RequestMethod.GET)
	public String detail(@PathVariable("seckillId")Long seckillId,Model model){
		if(seckillId==null)
			return "redirect:/seckill/list";
		SecKill seckill = service.getById(seckillId);
		if(null == seckill){
			return "redirect:/seckill/list";
		}		
		model.addAttribute("seckill",seckill);
		return "seckill/detail";
	}
	
	@RequestMapping(value="/{id}/exposer",method=RequestMethod.GET)
	@ResponseBody
	public HandlerResult exposer(@PathVariable("id")Long seckillId){
		HandlerResult result = new HandlerResult();
		try {
			Exposer exposer = service.exportSeckillUrl(seckillId);
			result.setRetCode(Constant.SYS_SUCCESS);
			result.setRetObj(exposer);
		} catch (Exception e) {
			e.printStackTrace();
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg(e.getMessage());
			return result;
		}
		return result;
	}
	
	@RequestMapping(value="/{id}/{md5}/execution",method=RequestMethod.POST)
	@ResponseBody
	public HandlerResult execute(@PathVariable("id")Long seckillId,@PathVariable("md5")String md5,@CookieValue(value="userPhone",required=false)Long phone){
		HandlerResult result = new HandlerResult();
		SecKillExecution execution;
		if(null == phone){
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("未注册");
		}
		try {
			execution = service.executeSeckill(seckillId,phone,md5);
			result.setRetCode(Constant.SYS_SUCCESS);
			result.setRetObj(execution);
		} catch(RepeatKillException e){
			execution=new SecKillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
			result.setRetCode(Constant.SYS_FAILED);
			result.setRetObj(execution);
		} catch (SeckillCloseException e) {
			execution=new SecKillExecution(seckillId, SeckillStateEnum.END);
			result.setRetCode(Constant.SYS_FAILED);
			result.setRetObj(execution);
		} catch (SeckillException e) {
			execution=new SecKillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
			result.setRetCode(Constant.SYS_FAILED);
			result.setRetObj(execution);
		}
		return result;
	}
	
	@RequestMapping(value="/time/now",method=RequestMethod.GET)
	@ResponseBody
	public HandlerResult getTime(){
		HandlerResult result = new HandlerResult();
		result.setRetCode(Constant.SYS_SUCCESS);
		result.setRetObj(new Date().getTime());
		return result;
	}
	
	
}
