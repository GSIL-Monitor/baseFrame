package com.tuniu.cms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.cms.dto.ArticleDto;
import com.tuniu.cms.model.Article;
import com.tuniu.cms.service.ArticleService;
import com.tuniu.cms.service.CategoryService;
import com.tuniu.common.dto.BaseDataDto;
import com.tuniu.common.model.BaseData;
import com.tuniu.common.model.Category;
import com.tuniu.common.service.BaseDataService;
import com.tuniu.common.service.ResourceService;
import com.tuniu.common.util.Constant;
import com.tuniu.common.util.HandlerResult;
import com.tuniu.upload.dto.AccordionDto;

@Controller
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	CategoryService service;
	
	@Autowired
	private BaseDataService bdService;
	
	@Autowired
	private ResourceService resService;
	
	@Autowired
	private ArticleService articleService;
	
	@RequestMapping("/articleManagement")
	public String articleManagement(Model model){
		List<AccordionDto> categories =  service.getAllCategoryList();
		BaseDataDto dto = new BaseDataDto();
		dto.setKey(Constant.CMS_TYPE);
		dto.setDelFlag(Constant.NO);
		List<BaseData> baseDataList = bdService.list(dto);
		model.addAttribute("categories", categories);
		model.addAttribute("bdList", resService.parse(baseDataList));
		return "cms/article/articleManagement";
	}
	
	/**
	 * 显示文章列表
	 */
	@RequestMapping("/{typeId}/{cid}")
	public String index(@PathVariable Integer typeId,@PathVariable Integer cid,ArticleDto dto,Model model){
		dto.setCid(cid);
		dto.setTypeId(typeId);
		articleService.loadPage(dto);
		model.addAttribute("dto", dto);
		//获取全路径
		Category category = service.getCategory( cid,typeId);
		System.out.println(service.getFullPath(category));
		model.addAttribute("title", service.getFullPath(category));
		return "cms/article/index";
	}
	
	/**
	 * 显示发表文章表单
	 */
	@RequestMapping(value="/addUI/{typeId}/{cid}",method=RequestMethod.GET)
	public String addUI(@PathVariable Integer typeId,@PathVariable Integer cid,@ModelAttribute("article")Article article){
		article.setTypeId(typeId);
		article.setCid(cid);
		return "cms/article/articleForm";
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public HandlerResult add(Article article){
		HandlerResult result = new HandlerResult();
		if(article.getAddPerson()==null || "".equals(article.getAddPerson().trim())){
			article.setAddPerson("佚名");
		}
		articleService.add(article);
		result.setRetCode(Constant.SYS_SUCCESS);
		result.setResMsg("发表文章成功");
		return result;
	}
	
	@RequestMapping("/detail/{id}")
	public String detail(@PathVariable Integer id,Model model){
		Article article = articleService.get(id);
		model.addAttribute("article", article);
		return "cms/article/detail";
	}
	
	@RequestMapping(value="/updateUI/{id}",method=RequestMethod.GET)
	public String update(@PathVariable Integer id,Model model){
		Article article = articleService.get(id);
		if(article.getUpdatePerson()==null || "".equals(article.getUpdatePerson().trim())){
			article.setUpdatePerson("佚名");
		}
		model.addAttribute("article", article);
		return "cms/article/articleForm";
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public HandlerResult update(Article article){
		HandlerResult result = new HandlerResult();
		articleService.update(article);
		result.setRetCode(Constant.SYS_SUCCESS);
		result.setResMsg("编辑文章成功");
		return result;
		
		
	}
	
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public String delete(@PathVariable Integer id){
		articleService.delete(id);
		return "文章删除成功";
	}
		
	
}
