package com.tuniu.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.common.dao.OperLogMapper;
import com.tuniu.common.dto.OperLogDto;
import com.tuniu.common.model.OperLog;
import com.tuniu.common.service.OperLogService;
@Service
public class OperLogServiceImpl implements OperLogService {
	@Autowired
	private OperLogMapper logMapper;

	@Override
	public void add(OperLog obj) {
		logMapper.add(obj);
	}

	@Override
	public void delete(Integer id) {

	}

	@Override
	public void update(OperLog obj) {

	}

	@Override
	public OperLog get(Integer id) {
		return logMapper.get(id);
	}

	@Override
	public List<OperLog> list(OperLogDto dto) {
		return logMapper.list(dto);
	}

	@Override
	public void loadPage(OperLogDto dto) {
		int totalRecords = logMapper.count(dto);
		List<OperLog> logList = logMapper.list(dto);
		dto.setTotalRecords(totalRecords);
		dto.setDataList(logList);

	}

}
