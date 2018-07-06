package com.tuniu.common.service;

import java.util.List;

import com.tuniu.common.dto.ResourceDto;
import com.tuniu.common.model.BaseData;
import com.tuniu.common.model.Category;
import com.tuniu.common.model.Resource;
import com.tuniu.common.service.base.BaseService;
import com.tuniu.upload.dto.AccordionDto;

public interface ResourceService extends BaseService<Resource, ResourceDto> {
	
	List<Resource> getResDataCache();
	
	Integer checkRelation(Integer roleId);
	
	List<Category> getCategoryList(int cid);
	
	List<AccordionDto> getAllCategoryList();
	
	void addCategory(Category category);
	
	Category getCategory(int id,int cid);

	void updateCategory(Category category);

	int checkCategoryRelation(Integer cid);

	void deleteCategory(Integer id);
	
	List<BaseData> parse(List<BaseData> baseDataList);

	String getFullPath(Category category);
	
}
