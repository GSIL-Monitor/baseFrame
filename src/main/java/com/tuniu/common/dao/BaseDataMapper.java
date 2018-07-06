package com.tuniu.common.dao;

import java.util.List;

import com.tuniu.common.dao.base.BaseMapper;
import com.tuniu.common.dto.BaseDataDto;
import com.tuniu.common.model.BaseData;

public interface BaseDataMapper extends BaseMapper<BaseData, BaseDataDto> {
	int checkExists(BaseData baseData);
	List<BaseData> listBaseData(BaseDataDto dto);
}
