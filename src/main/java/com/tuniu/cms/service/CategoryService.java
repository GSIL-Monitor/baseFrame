package com.tuniu.cms.service;
import java.util.List;

import com.tuniu.common.dto.ResourceDto;
import com.tuniu.common.model.Category;
import com.tuniu.common.model.Resource;
import com.tuniu.common.service.base.BaseService;
import com.tuniu.upload.dto.AccordionDto;

public interface CategoryService extends BaseService<Resource, ResourceDto> {
	Category getCategory(int id,int cid);
	List<Category> getCategoryList(int cid);
	void addCategory(Category category);
	void updateCategory(Category category);
	void deleteCategory(Integer id);
	List<AccordionDto> getAllCategoryList();
	public String getFullPath(Category category);
	int checkCategoryRelation(int id);
}

