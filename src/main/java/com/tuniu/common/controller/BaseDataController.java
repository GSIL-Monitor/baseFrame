package com.tuniu.common.controller;

import java.io.File;
import java.util.Calendar;
import java.util.UUID;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tuniu.common.dto.BaseDataDto;
import com.tuniu.common.model.ActiveUser;
import com.tuniu.common.model.BaseData;
import com.tuniu.common.service.BaseDataService;
import com.tuniu.common.util.Constant;
import com.tuniu.common.util.HandlerResult;
import com.tuniu.common.util.ResourceUtil;

@Controller
@RequestMapping("/common/baseData")
public class BaseDataController {
	
	@Autowired
	private BaseDataService service;
	
	@Value("${image.upload.dirtectory}")
	private String root;
	
	@RequestMapping("/index")
	@RequiresPermissions("baseData:view")
	public String index(@ModelAttribute("dto") BaseDataDto dto){
		service.loadPage(dto);
		return "common/baseData/baseDataManagement";		
	}
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	@RequiresPermissions("baseData:add")
	public String add(@ModelAttribute("baseData")BaseData baseData){
		baseData.setKeyList(ResourceUtil.getKeyList("baseData"));
		return "common/baseData/baseDataForm";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@RequiresPermissions("baseData:add")
	@ResponseBody
	public HandlerResult add(BaseData baseData,MultipartFile file) throws Exception{
		ActiveUser user = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
		baseData.setAddPerson(user.getUsername());
		HandlerResult result = new HandlerResult();
		if(service.checkExists(baseData)){
			result.setRetCode(Constant.SYS_FAILED);
			result.setResMsg("key对应value重复");
		}else{
			baseData.setUrl(upload(file));
			service.add(baseData);
			result.setRetCode(Constant.SYS_SUCCESS);
		}
		return result;
	}
	
	private String upload(MultipartFile file) throws Exception{		
		if(file!=null && !file.isEmpty()){
			//图片上传
			String orginalFileName = file.getOriginalFilename();
			String newFileName = UUID.randomUUID().toString() + orginalFileName.substring(orginalFileName.lastIndexOf("."));
			//上传图片
//			String root = Constant.UPLOAD_IMAGES_PATH;//根目录
			String absFilePath = root + getDir() + newFileName;
			File pic = new File(absFilePath);
			File thumb_pic = new File(root + getDir() + "thumb_" + newFileName);
			if(!pic.exists()){
				pic.mkdirs();
			}
			//向磁盘写文件
			file.transferTo(pic);
			//生成缩略图
			Thumbnails.of(pic).size(25, 25).toFile(thumb_pic);
			return (getDir() + newFileName);
		}
		return null;
	}
	
	//生成图片分级目录 /年/月/日/时
	private String getDir(){
		Calendar c = Calendar.getInstance();		
		StringBuffer buffer = new StringBuffer();
		buffer.append(c.get(Calendar.YEAR) + "/");
		buffer.append((c.get(Calendar.MONTH)+1) + "/");
		buffer.append(c.get(Calendar.DAY_OF_MONTH) + "/");
		buffer.append(c.get(Calendar.HOUR_OF_DAY) + "/");
		return buffer.toString();
	}
	
	@RequestMapping(value="/{id}/update",method=RequestMethod.GET)
	@RequiresPermissions("baseData:update")
	public String update(@PathVariable("id")Integer id,Model model){
		BaseData baseData = service.get(id);
		baseData.setKeyList(ResourceUtil.getKeyList("baseData"));
		model.addAttribute("baseData", baseData);
		return "common/baseData/baseDataForm";
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@RequiresPermissions("baseData:update")
	@ResponseBody
	public HandlerResult update(BaseData baseData,MultipartFile file) throws Exception{
		baseData.setUrl(upload(file));
		service.update(baseData);
		HandlerResult result = new HandlerResult();
		result.setRetCode(Constant.SYS_SUCCESS);
		return result;
	}
	
	@RequestMapping("/{id}/chgFlag/{val}")
	@RequiresPermissions("baseData:chgFlag")
	@ResponseBody
	public HandlerResult chgFlag(@PathVariable("id")Integer id,@PathVariable("val")Integer val,@ModelAttribute("baseData")BaseData baseData){		
		baseData.setDelFlag(val);
		service.update(baseData);
		HandlerResult result = new HandlerResult();
		result.setRetCode(Constant.SYS_SUCCESS);
		return result;
	}
	
	@RequestMapping("/{id}/delete")
	@RequiresPermissions("baseData:delete")
	@ResponseBody
	public HandlerResult delete(@PathVariable("id")Integer id){
		//TODO
		//删除前需要判断是否已引用，
		//若有图片，待完成物理图片文件删除
		service.delete(id);
		HandlerResult result = new HandlerResult();
		result.setRetCode(Constant.SYS_SUCCESS);
		return result;
	}

}
