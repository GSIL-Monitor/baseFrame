package com.tuniu.common.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.tuniu.common.dao.base.BaseMapper;
import com.tuniu.common.dto.ResourceDto;
import com.tuniu.common.model.Category;
import com.tuniu.common.model.Resource;

public interface ResourceMapper extends BaseMapper<Resource, ResourceDto> {

	Set<String> findPermissionsByUserName(String userName);

	List<Resource> findMenus(List<String> permissions);
	
	Integer checkRelation(Integer roleId);
	
	List<Category> getCategoryList(@Param("cid")int cid);
	
	List<Category> getAllCategoryList();
	
	void addCategory(Category category);
	
	Category getCategory(@Param("id")int id,@Param("cid")int cid);
	
	void updateCategory(Category category);
	
	int checkCategoryRelation(@Param("id")Integer id);

	void deleteCategory(@Param("id")Integer id);
}
