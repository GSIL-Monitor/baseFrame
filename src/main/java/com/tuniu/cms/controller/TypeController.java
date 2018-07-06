package com.tuniu.cms.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tuniu.cms.service.CategoryService;
import com.tuniu.common.dto.BaseDataDto;
import com.tuniu.common.model.BaseData;
import com.tuniu.common.model.Category;
import com.tuniu.common.model.Resource;
import com.tuniu.common.service.BaseDataService;
import com.tuniu.common.util.Constant;

@Controller
@RequestMapping("/cms/type")
public class TypeController {
	
	@Autowired
	private BaseDataService baseDataService;
	
	@Autowired
	private CategoryService service;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request){
		BaseDataDto dto = new BaseDataDto();
		dto.setKey(Constant.CMS_TYPE);
		dto.setDelFlag(Constant.NO);
		List<Resource> list = new ArrayList<>();
		for(BaseData bd : baseDataService.list(dto)){
			String jsonStr = bd.getValue();
			JSONObject json = JSON.parseObject(jsonStr);
			String id = json.getString("id");
			String name = json.getString("name");
			String url = json.getString("url");
			Resource res = new Resource();
			res.setType(id);
			res.setName(name);
			res.setUrl(url);
			list.add(res);
		}
		request.setAttribute("list", list);
		return "cms/type/index";
	}
	
	@RequestMapping("/index/{cid}")
	public String indexCategory(@PathVariable("cid")Integer cid,HttpServletRequest request){
		request.setAttribute("cid", cid);
		request.setAttribute("categoryList", service.getCategoryList(cid));
		return "cms/type/management";
	}
	
	/** 栏目分类 - 显示新增 */
	@RequestMapping("/{pid}/{cid}/toAddChild/{ids}")
	public String toAddCategoryChild(@PathVariable("pid")Integer pid,@PathVariable("cid")Integer cid,@PathVariable("ids")String ids,Model model){
		Category category = new Category();
		if(service.getCategory(pid,cid)!=null){
			category.setpName(service.getCategory(pid,cid).getName());
		}else{
			category.setpName("root");
		}
		category.setParentid(pid);
		category.setParentids(ids);
		category.setAvailable(Constant.YES);
		category.setCid(cid);
		model.addAttribute("category",category);
		model.addAttribute("menuList",service.getCategoryList(cid));
		return "cms/type/categoryForm";
		
	}
	
	/** 栏目分类 - 新增 */
	@RequestMapping("/addCategoryChild")
	@ResponseBody
	public String addCategoryChild(Category category){
		String path = category.getParentids().replace(',', '/');
		category.setParentids(path);		
		service.addCategory(category);
		//自动生成url 
		String url = "article/"+category.getCid() + "/" + category.getId();
		category.setUrl(url);
		service.updateCategory(category);
		return "Success";
	}
	
	/** 栏目分类 - 显示修改 */
	@RequestMapping("{id}/{cid}/toUpdateChild/{ids}")
	public String toUpdateCategoryChild(@PathVariable("id") Integer id,@PathVariable("cid") Integer cid,@PathVariable("ids")String pids, Model model){
		Category category = service.getCategory(id, cid);
		if(category!=null && service.getCategory(category.getParentid(), cid)!=null){
			category.setpName(service.getCategory(category.getParentid(), cid).getName());
		}else
			category.setpName("root");
		category.setParentids(pids);
		category.setCid(cid);
		model.addAttribute("category",category);
		model.addAttribute("menuList", service.getCategoryList(cid));
		return "cms/type/categoryForm";
	}
	
	/** 栏目分类 - 修改 */
	@RequestMapping("/updateCategoryChild")
	@ResponseBody
	public String updateCategoryChild(Category category){
		String path=category.getParentids().replace(',', '/') + "/";
		category.setParentids(path.substring(0, path.lastIndexOf("/")));
		service.updateCategory(category);
		return "Success";
	}
	
	/** 栏目分类 - 删除 */
	@RequestMapping("{id}/{cid}/delete")
	@ResponseBody
	public String delete(@PathVariable("id")Integer id,@PathVariable("cid")Integer cid){
		//校验分类是否关联内容文章，关联则不允许删除
		if(service.checkCategoryRelation(id)>0){
			return "Failure";
		}
		service.deleteCategory(id);
		return "Success";
	}

}
