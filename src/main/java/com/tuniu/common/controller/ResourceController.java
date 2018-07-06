package com.tuniu.common.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tuniu.common.dto.BaseDataDto;
import com.tuniu.common.dto.ResourceDto;
import com.tuniu.common.model.BaseData;
import com.tuniu.common.model.Category;
import com.tuniu.common.model.Resource;
import com.tuniu.common.service.BaseDataService;
import com.tuniu.common.service.ResourceService;
import com.tuniu.common.util.Constant;

@Controller
@RequestMapping("/common/resource")
public class ResourceController {
	@Autowired
	private ResourceService service;
	
	@Autowired
	private BaseDataService baseDataService;
	
	@RequestMapping("/default")
	@RequiresPermissions("res:view")
	public String defaultPage(HttpServletRequest request){
		BaseDataDto dto = new BaseDataDto();
		dto.setKey(Constant.FILE_TYPE);
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
		return "common/resource/index";
	}

	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		List<Resource> resourceList = service.list(new ResourceDto());
		request.setAttribute("resourceList", resourceList);
		return "common/resource/resourceManagement";
	}
	
	@RequestMapping("/index/{cid}")
	public String indexCategory(@PathVariable("cid")Integer cid,HttpServletRequest request){
		request.setAttribute("cid", cid);
		request.setAttribute("resourceList", service.getCategoryList(cid));
		return "common/resource/categoryManagement";
	}
	
	/** 资源分类 - 显示新增 */
	@RequestMapping("/{pid}/{cid}/toAddChild/{ids}")
	public String toAddCategoryChild(@PathVariable("pid")Integer pid,@PathVariable("cid")Integer cid,@PathVariable("ids")String ids,Model model){
//		Category category = new Category();
		Category category = service.getCategory(pid, cid);
		if(category!=null){
			category.setpName(category.getName());
			category.setName(null);
			category.setId(null);
		}else{
			category = new Category();
			category.setpName("root");
		}
		category.setParentid(pid);
		category.setParentids(ids);
		category.setAvailable(Constant.YES);
		category.setCid(cid);
		model.addAttribute("category", category);
		model.addAttribute("menuList",service.getCategoryList(cid));
		return "common/resource/categoryForm";
	}

	/** 资源管理 - 显示新增 */
	@RequestMapping("/{pid}/toAddChild/{ids}")
	@RequiresPermissions("res:add")
	public String toAddChild(@PathVariable("pid") Integer pid,@PathVariable("ids")String pids, Model model) {
		Resource resource = new Resource();
		resource.setPid(pid);
		if(service.get(pid)!=null)
			resource.setpName(service.get(pid).getName());
		else
			resource.setpName("root");
		resource.setPids(pids);
		resource.setAvailable(Constant.YES);
		resource.setType(Constant.RESOURCE_MENU_TYPE);
		model.addAttribute("resource", resource);
		model.addAttribute("menuList",getMenuList());
		return "common/resource/resourceForm";
	}
	
	private List<Resource> getMenuList(){
		//获取菜单树
		ResourceDto dto=new ResourceDto();
		//dto.setType("menu");
		return service.list(dto);
	}
	
	
	@RequestMapping("/addChild")
	@RequiresPermissions("res:add")
	@ResponseBody
	public String addChild(Resource resource,HttpServletRequest request){
		String path = resource.getPids().replace(',', '/');
		//resource.setPids(path.substring(0, path.lastIndexOf("/")));
		resource.setPids(path);
		service.add(resource);
		return "Success";
	}
	
	/** 资源分类 - 新增 */
	@RequestMapping("/addCategoryChild")
	@ResponseBody
	public String addCategoryChild(Category category){
		String path = category.getParentids().replace(',', '/');
		category.setParentids(path);
		service.addCategory(category);
		//自动生成url 
		String url = "file/index/"+category.getId() + "/" + category.getCid();
		category.setUrl(url);
		service.updateCategory(category);
		return "Success";
	}
	
	@RequestMapping("/{id}/toUpdateChild/{ids}")
	@RequiresPermissions("res:update")
	public String toUpdate(@PathVariable Integer id,@PathVariable("ids")String pids, HttpServletRequest request) {
		Resource resource = service.get(id);
		if(service.get(resource.getPid())!=null)
				resource.setpName(service.get(resource.getPid()).getName());
		resource.setPids(pids);
		request.setAttribute("resource", resource);
		request.setAttribute("menuList",getMenuList());
		return "common/resource/resourceForm";
	}
	
	/** 资源分类 - 显示修改 */
	@RequestMapping("/{id}/{cid}/toUpdateChild/{ids}")
	public String toUpdateCategoryChild(@PathVariable("id") Integer id,@PathVariable("cid") Integer cid,@PathVariable("ids")String pids, Model model){
		Category category=service.getCategory(id, cid);
		if(category!=null && service.getCategory(category.getParentid(), cid)!=null){
			category.setpName(service.getCategory(category.getParentid(), cid).getName());
		}else
			category.setpName("root");
		category.setParentids(pids);
		category.setCid(cid);
		model.addAttribute("category", category);
		model.addAttribute("menuList",service.getCategoryList(cid));
		return "common/resource/categoryForm";
	}
	
	@RequestMapping("/updateChild")
	@RequiresPermissions("res:update")
	@ResponseBody
	public String update(Resource resource) {
		String path = resource.getPids().replace(',', '/');
		resource.setPids(path.substring(0, path.lastIndexOf("/")));
//		resource.setPids(resource.getPids().replace(',', '/'));
		service.update(resource);
        return "Success";
	}
	
	/** 资源分类 - 修改 */
	@RequestMapping("/updateCategoryChild")
	@ResponseBody
	public String updateCategoryChild(Category category){
		String path = category.getParentids().replace(',', '/') + "/";
		//TODO
		//结点需要同步更新parentids路径
		category.setParentids(path.substring(0, path.lastIndexOf("/")));
		service.updateCategory(category);
		return "Success";
	}
	
	@RequestMapping("/{id}/delete")
	@RequiresPermissions("res:delete")
	@ResponseBody
	public String delete(@PathVariable Integer id, HttpServletRequest request) {
		//校验角色是否关联用户, 如果关联则不允许删除
		if(service.checkRelation(id)>0){
			return "Failure";
		}
		service.delete(id);
		return "Success";
	}
	
	/** 资源分类 - 删除 */
	@RequestMapping("/{id}/{cid}/delete")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id,@PathVariable("cid") Integer cid){
		
		//校验分类是否关联资源，关联则不允许删除
		if(service.checkCategoryRelation(id)>0){
			return "Failure";
		}
		service.deleteCategory(id);
		return "Success";
	}

}
