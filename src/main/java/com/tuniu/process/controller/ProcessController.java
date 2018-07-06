package com.tuniu.process.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tuniu.process.service.ProcessService;

@Controller
@RequestMapping("/proc")
public class ProcessController {
	
	@Autowired
	private ProcessService processService;

	@RequestMapping("/index")
	public String index(HttpServletRequest request){
		List<ProcessDefinition> pdList = processService.processDefinitionList();
		
		List<Deployment> deployList = processService.deploymentList();
		request.setAttribute("pdList", pdList);
		request.setAttribute("deployList", deployList);
		return "process/index";
	}
	
	@RequestMapping("/deploy")
	public String deploy(MultipartFile file,String name)throws Exception{
		if(file!=null && !file.isEmpty()){
			ZipInputStream zip = new ZipInputStream(file.getInputStream());
			Deployment deployment = processService.deployment(zip, name);
			System.out.println(deployment.getId() + " : " + deployment.getName());
		}
		return "redirect:/proc/index";
	}
	
	@RequestMapping("/{deployId}/view")
	public void view(@PathVariable("deployId")String id,HttpServletResponse response) throws IOException{
		String resourceName = processService.getDiagramResourceName(id);
		//获取流程定义图片的输入流
		InputStream in = processService.getResourceAsStream(id, resourceName);
		OutputStream out = response.getOutputStream();
		for(int b=-1;(b=in.read()) != -1;){
			out.write(b);
		}
		out.close();
		in.close();
	}
	
	@RequestMapping("/{deployId}/delete")
	@ResponseBody
	public String delete(@PathVariable("deployId")String deploymentId){
		processService.deleteProcessByDeployId(deploymentId);
		return "success";
	}
}
