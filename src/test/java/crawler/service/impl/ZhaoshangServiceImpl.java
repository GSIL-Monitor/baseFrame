package crawler.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crawler.dao.ZhaoshangMapper;
import crawler.model.Site;
import crawler.service.BaseService;

@Service
public class ZhaoshangServiceImpl implements BaseService<Site> {
	
	@Autowired
	private ZhaoshangMapper mapper;

	@Override
	public void add(Site obj) {
		mapper.add(obj);
	}

	@Override
	public void update(Site obj) {
		
	}

}
