package com.tuniu.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.business.dao.LeaveBillMapper;
import com.tuniu.business.dto.LeaveBillDto;
import com.tuniu.business.model.LeaveBill;
import com.tuniu.business.service.LeaveBillService;

@Service
public class LeaveBillServiceImpl implements LeaveBillService {
	
	@Autowired
	private LeaveBillMapper mapper;

	@Override
	public void add(LeaveBill obj) {
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(LeaveBill obj) {
		mapper.update(obj);
	}

	@Override
	public LeaveBill get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<LeaveBill> list(LeaveBillDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void loadPage(LeaveBillDto dto) {
		int totalRecords = mapper.count(dto);
		List<LeaveBill> dataList = list(dto);
		dto.setTotalRecords(totalRecords);
		dto.setDataList(dataList);
	}

}
