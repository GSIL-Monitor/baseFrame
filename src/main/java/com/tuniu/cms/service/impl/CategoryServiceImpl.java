package com.tuniu.cms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.cms.dao.CategoryMapper;
import com.tuniu.cms.service.CategoryService;
import com.tuniu.common.dto.ResourceDto;
import com.tuniu.common.model.BaseData;
import com.tuniu.common.model.Category;
import com.tuniu.common.model.Resource;
import com.tuniu.upload.dto.AccordionDto;
import com.tuniu.util.ParseJson;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryMapper mapper;
	
	@Override
	public void add(Resource obj) {

	}

	@Override
	public void delete(Integer id) {

	}

	@Override
	public void update(Resource obj) {

	}

	@Override
	public Resource get(Integer id) {
		return null;
	}

	@Override
	public List<Resource> list(ResourceDto dto) {
		return null;
	}

	@Override
	public void loadPage(ResourceDto dto) {

	}

	@Override
	public List<Category> getCategoryList(int cid) {
		return mapper.getCategoryList(cid);
	}

	@Override
	public Category getCategory(int id, int cid) {
		return mapper.getCategory(id, cid);
	}
	
	@Override
	public String getFullPath(Category category) {
		BaseData bd = ParseJson.toBaseData(category.getCategory().getValue());
		int cid = category.getCategory().getId();
		String pids = category.getParentids();
		String[] pidArr =  pids.split("/");
		String pathName = " - ";
		for(int i=1;i<pidArr.length;i++){
			pathName += mapper.getCategory(Integer.parseInt(pidArr[i]), cid).getName() + " - ";			
		}
		
		return bd.getValue() + pathName + category.getName();
	}

	@Override
	public void addCategory(Category category) {
		mapper.addCategory(category);
	}

	@Override
	public void updateCategory(Category category) {
		mapper.updateCategory(category);
	}

	@Override
	public void deleteCategory(Integer id) {
		mapper.deleteCategory(id);
	}

	@Override
	public List<AccordionDto> getAllCategoryList() {
		List<Category> categories = mapper.getAllCategoryList();
		List<AccordionDto> dtoList = new ArrayList<>();
		for(Category category : categories){
			AccordionDto dto = new AccordionDto();
			dto.setCategory(category);
			dto.setFileType(ParseJson.toBaseData(category.getCategory().getValue()));
			dtoList.add(dto);
		}
		
		return dtoList;
	}

	@Override
	public int checkCategoryRelation(int id) {
		return mapper.checkCategoryRelation(id);
	}
	
	

}
