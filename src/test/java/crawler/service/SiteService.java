package crawler.service;

import java.util.List;

public interface SiteService<T> {
		
	void execute();	//网站爬取数据,业务逻辑入口
	String run(T obj);	//访问url,得到html
	List<T> parseHtml(String result);//解析html
	void save(T obj);	//数据处理，保存入库
}

