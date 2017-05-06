package cn.edu.zzia.career.base;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import cn.edu.zzia.career.tools.GenericClass;
import cn.edu.zzia.career.tools.StringUtils;


@SuppressWarnings("unchecked")
public class CommonDaoImpl<T> extends HibernateDaoSupport implements ICommonDao<T> {
	
	/**获取父类的泛型类型***/
	private Class entityClass=GenericClass.getGenericClass(this.getClass());
	
	/*
	 *  <bean id="xx" class="cn.itcast.crm.dao.impl.CommonDaoImpl">
	      <property name="sessionFactory" ref="sessionFactory"></property>
	    </bean>
	 */
	@Resource(name="sessionFactory")
	public  void setSessionFactoryDI(SessionFactory sessionFactory) {
	    //调用父类的setSessionFactory方法,注入sessionFactory
		super.setSessionFactory(sessionFactory);
	}

	public void save(T entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
	}
	

	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}

	public T findObjectById(Serializable id) {
		if(id==null){
			throw new RuntimeException("您要查找的["+id+"]不能为空");
		}
		return (T)this.getHibernateTemplate().get(entityClass, id);
	}

	/**
	 * 批量删除,通过多id删除多条记录
	 */
	public void deleteByIds(Serializable... ids) {
		if(ids!=null&&ids.length>0){
			for(Serializable id:ids){
				Object entity=this.getHibernateTemplate().get(entityClass, id);
				if(entity==null){
					throw new RuntimeException("您要查找的["+id+"]不存在"); 
				}
				this.getHibernateTemplate().delete(entity);
			}
		}
	}
	public void deleteAllObjects(Collection<T> entities) {
	   this.getHibernateTemplate().deleteAll(entities);
	}

	public List<T> findObjectsByConditionWithNoPage(String whereHql, final Object[] params, 
			   LinkedHashMap<String, String> orderby) {
		
		//产生select语句 SELECT * FROM SysUserGroup o WHERE 
		String hql="select o from "+entityClass.getSimpleName() +"  o  where 1=1  ";
		//System.out.println("hql  "+hql);
		
		//在select语句的后面这查询条件
		if(StringUtils.isNotBlank(whereHql)){
			hql=hql+whereHql;
		}
		//处理排序
		String orderbystr=buildOrderBy(orderby);
		//System.out.println("orderbystr  "+orderbystr);
		hql=hql+orderbystr;
		//System.out.println("hql  "+hql);
		
		final String fhql=hql;
		
		List<T> list = (List<T>)this.getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				Query query=session.createQuery(fhql);
				//设置参数 
				setParams(query,params);
				return  query.list();
			}
		});
		
		return list;
	}

	
	public List<T> findObjectsByConditionWithNoPage(String whereHql,Object[] params) {
		return this.findObjectsByConditionWithNoPage(whereHql, params,null);
	}
	

	public List<T> findObjectsByConditionWithNoPage() {
		return this.findObjectsByConditionWithNoPage(null, null,null);
	}
	
	public List<T> findObjectsByConditionWithNoPage(LinkedHashMap<String, String> orderby) {
		return this.findObjectsByConditionWithNoPage(null, null,orderby);
	}
	
	/**
	 * 设置hql语句需要的参数
	 * @param query
	 * @param params
	 */
	public void setParams(Query query, Object[] params) {
		if(params!=null&&params.length>0){
			for(int i=0;i<params.length;i++){
				query.setParameter(i, params[i]);
			}
		}
	}
	
	/*
	 *  组织排序条件
	 *  orderby.put("o.id", "asc");
		orderby.put("o.name", "desc");
	 */
	public String buildOrderBy(LinkedHashMap<String, String> orderby) {
		StringBuffer buf=new StringBuffer("");
		if(orderby!=null&&!orderby.isEmpty()){
			buf.append("  order by ");
			for(Map.Entry<String, String> em:orderby.entrySet()){
				buf.append(em.getKey()+"  "+em.getValue()+",");
			}
			//去掉最后一个","
			buf.deleteCharAt(buf.length()-1);
		}
		return buf.toString();
	}

	/**
	 *  处理待二级缓存的查询
	 * @param whereHql
	 * @param params
	 * @param orderby
	 * @return
	 */
	public List<T> findObjectsByConditionWithNoPageCache(String whereHql, final Object[] params, 
			   LinkedHashMap<String, String> orderby) {
		
		//产生select语句 SELECT * FROM SysUserGroup o WHERE 
		String hql="select o from "+entityClass.getSimpleName() +"  o  where 1=1  ";
		
		//在select语句的后面这查询条件
		if(StringUtils.isNotBlank(whereHql)){
			hql=hql+whereHql;
		}
		//处理排序
		String orderbystr=buildOrderBy(orderby);
		hql=hql+orderbystr;
		System.out.println("二级缓存 hql  "+hql);
		final String fhql=hql;
		List<T> list = (List<T>) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException {
				Query query=session.createQuery(fhql);
				//使用查询缓存,必须加该行代码
				query.setCacheable(true);
				//设置参数 
				setParams(query,params);
				return query.list();
			}
		});
		return list;
	}

	
}
