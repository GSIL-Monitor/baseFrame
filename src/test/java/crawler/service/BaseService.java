package crawler.service;

public interface BaseService<T> {
	void add(T obj);
	void update(T obj);
}
