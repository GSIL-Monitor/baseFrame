package com.tuniu.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.common.dao.ResourceMapper;
import com.tuniu.common.dto.ResourceDto;
import com.tuniu.common.model.BaseData;
import com.tuniu.common.model.Category;
import com.tuniu.common.model.Resource;
import com.tuniu.common.service.ResourceService;
import com.tuniu.upload.dto.AccordionDto;
import com.tuniu.util.ParseJson;

@Service
public class ResourceServiceImpl implements ResourceService {
	
	@Autowired
	private ResourceMapper mapper;
	
	@Override
	public void add(Resource res) {
		mapper.add(res);
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(Resource res) {
		mapper.update(res);
	}

	@Override
	public Resource get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<Resource> list(ResourceDto dto) {
		return mapper.list(dto);
	}

	@Override
	public List<Resource> getResDataCache() {
		List<Resource> resList = mapper.list(new ResourceDto());
		return resList;
	}

	@Override
	public void loadPage(ResourceDto dto) {
		
	}

	@Override
	public Integer checkRelation(Integer roleId) {
		return mapper.checkRelation(roleId);
	}

	@Override
	public List<Category> getCategoryList(int cid) {
		return mapper.getCategoryList(cid);
	}

	@Override
	public void addCategory(Category category) {
		mapper.addCategory(category);
	}

	@Override
	public Category getCategory(int id, int cid) {
		return mapper.getCategory(id, cid);
	}

	@Override
	public void updateCategory(Category category) {
		mapper.updateCategory(category);
	}

	@Override
	public int checkCategoryRelation(Integer id) {
		return mapper.checkCategoryRelation(id);
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
	public List<BaseData> parse(List<BaseData> baseDataList) {
		List<BaseData> bdList=new ArrayList<>();
		for(BaseData bd : baseDataList){
			String jsonStr = bd.getValue();
			bdList.add(ParseJson.toBaseData(jsonStr));
		}
		
		return bdList;
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

}
