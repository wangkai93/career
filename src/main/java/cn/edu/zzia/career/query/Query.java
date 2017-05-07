package cn.edu.zzia.career.query;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


/**
 * 
 * @author zj
 * @date 2016年12月25日 下午1:26:27
 * @desc Query.java
 */
public class Query {

	private static final Integer LIMIT = 10;
	
	private Integer page = 0;
	
	private Integer limit = LIMIT;
	
	private Integer offset = 0;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		if(page < 0){
			page = 0;
		}
		this.page = page;
		
		this.offset = this.page * this.limit;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		if(limit < 0){
			limit = LIMIT;
		}
		this.limit = limit;
	}

	public Integer getOffset() {
		if(this.page < 0 || this.offset < 0){
			return 0;
		}
		return this.page * this.limit;
	}

	public void setOffset(Integer offset) {
		
		if(offset < 0){
			offset = 0;
		}
		this.offset = offset;
	}
	
	 public Map<String, Object> toMap() {    
	  
	        Map<String, Object> map = new HashMap<String, Object>();   
			try {
				BeanInfo beanInfo = Introspector.getBeanInfo(this.getClass());
				PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();    
		        for (PropertyDescriptor property : propertyDescriptors) {    
		            String key = property.getName();    
		            if (key.compareToIgnoreCase("class") == 0) {   
		                continue;  
		            }  
		            Method getter = property.getReadMethod();  
		            Object value = getter!=null ? getter.invoke(this) : null;  
		            map.put(key, value);  
		        }    
			} catch (IntrospectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    
	        return map;  
	    }    
	 
	 public static void main(String[] arg0) throws Exception{
		 Query q = new Query();
		 Map<String, Object> map = q.toMap();
		 for(String key : map.keySet()){
			 System.out.println("key = " + key + " value = " + map.get(key));
		 }
	 }
	
}
