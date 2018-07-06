package com.tuniu.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.cms.dao.ArticleMapper;
import com.tuniu.cms.dto.ArticleDto;
import com.tuniu.cms.model.Article;
import com.tuniu.cms.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {
	
	@Autowired
	private ArticleMapper mapper;

	@Override
	public void add(Article obj) {
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(Article obj) {
		mapper.update(obj);

	}

	@Override
	public Article get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<Article> list(ArticleDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void loadPage(ArticleDto dto) {
		int totalRecords = mapper.count(dto);
		List<Article> dataList = list(dto);
		dto.setTotalRecords(totalRecords);
		dto.setDataList(dataList);
	}

}
