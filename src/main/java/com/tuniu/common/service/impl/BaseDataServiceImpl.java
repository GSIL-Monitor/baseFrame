package com.tuniu.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.common.dao.BaseDataMapper;
import com.tuniu.common.dto.BaseDataDto;
import com.tuniu.common.model.BaseData;
import com.tuniu.common.service.BaseDataService;
import com.tuniu.common.util.ResourceUtil;

@Service
public class BaseDataServiceImpl implements BaseDataService {
	
	@Autowired
	private BaseDataMapper baseDataMapper;

	@Override
	public void add(BaseData baseData) {
		baseDataMapper.add(baseData);				
	}

	@Override
	public void delete(Integer id) {
		baseDataMapper.delete(id);
	}

	@Override
	public void update(BaseData baseData) {
		baseDataMapper.update(baseData);
	}

	@Override
	public BaseData get(Integer id) {
		return baseDataMapper.get(id);
	}

	@Override
	public List<BaseData> list(BaseDataDto dto) {
		return baseDataMapper.listBaseData(dto);
	}

	@Override
	public void loadPage(BaseDataDto dto) {
		int totalRecords = baseDataMapper.count(dto);
		List<BaseData> dataList = list(dto);
		dto.setTotalRecords(totalRecords);
		dto.setDataList(dataList);
		dto.setKeyList(ResourceUtil.getKeyList("baseData"));
	}

	@Override
	public boolean checkExists(BaseData baseData) {
		return baseDataMapper.checkExists(baseData) > 0 ? true:false;
		
	}

}
