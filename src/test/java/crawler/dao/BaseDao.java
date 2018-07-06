package crawler.dao;

public interface BaseDao<T> {

	void add(T obj);
	void update(T obj);
}

