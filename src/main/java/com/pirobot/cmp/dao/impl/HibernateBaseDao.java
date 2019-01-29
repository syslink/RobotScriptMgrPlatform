/**
 * probject:cim
 * @version 2.0.0
 * 
 * @author 3979434@qq.com
 */ 
package com.pirobot.cmp.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
@SuppressWarnings("unchecked")
public class HibernateBaseDao<T> extends HibernateDaoSupport {
	private Class<T> entityClass;

	@Autowired  
    public void setSessionFactoryOverride(SessionFactory sessionFactory)  
    {  
        super.setSessionFactory(sessionFactory);  
    }  
	
	public HibernateBaseDao() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		entityClass = (Class) params[0];
	}

	public T get(Serializable id) {
		return (T) getHibernateTemplate().get(entityClass, id);
	}
	
	 
	public List<T> getAll() {
		return getHibernateTemplate().loadAll(entityClass);
	}

	public void save(Object o) {
		getHibernateTemplate().save(o);
	}
	
	public void saveOrUpdate(Object o) {
		getHibernateTemplate().saveOrUpdate(o);
	}

	public void merge(Object o) {
		currentSession().merge(o);
	}
	public void delete(Object o) {
		getHibernateTemplate().delete(o);
	}
 
	public void update(Object o) {
		

		
		getHibernateTemplate().update(o);
	}
}
