package com.tuniu.common.service;

import com.tuniu.common.dto.BaseDataDto;
import com.tuniu.common.model.BaseData;
import com.tuniu.common.service.base.BaseService;

public interface BaseDataService extends BaseService<BaseData, BaseDataDto>{
	boolean checkExists(BaseData baseData);
}
