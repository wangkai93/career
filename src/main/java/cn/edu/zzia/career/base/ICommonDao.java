package cn.edu.zzia.career.base;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

public interface ICommonDao<T> {
	void save(T entity);

	void update(T entity);

	T findObjectById(Serializable id);

	void deleteByIds(Serializable... ids);

	void deleteAllObjects(Collection<T> entities);

	List<T> findObjectsByConditionWithNoPage(String whereHql, Object[] params, LinkedHashMap<String, String> orderby);

	List<T> findObjectsByConditionWithNoPage(String whereHql, Object[] params);

	List<T> findObjectsByConditionWithNoPage(LinkedHashMap<String, String> orderby);

	List<T> findObjectsByConditionWithNoPage();

	// 使用二级缓存
	List<T> findObjectsByConditionWithNoPageCache(String whereHql, Object[] params,
			LinkedHashMap<String, String> orderby);

}
