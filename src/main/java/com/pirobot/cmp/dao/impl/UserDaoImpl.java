 /**
 * probject:cim
 * @version 2.0.0
 * 
 * @author 3979434@qq.com
 */
package com.pirobot.cmp.dao.impl;


import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.pirobot.framework.web.Page;
import com.pirobot.cmp.model.User;

@SuppressWarnings("unchecked")
@Repository
public class UserDaoImpl extends HibernateBaseDao<User>{

	final String INSERT_SQL = "insert into "+User.TABLE_NAME+"(account,name,gender,telephone,email,orgCode,password) values(?,?,?,?,?,?,?)";

	public User queryByAccount(String account) {
		DetachedCriteria criteria=DetachedCriteria.forClass(User.class);
		criteria.add(Restrictions.eq("account", account));
		 
		List<User> list = (List<User>) getHibernateTemplate().findByCriteria(criteria);
		return (list == null || list.isEmpty())?null:list.get(0);
	}

	public long getMaxUserId()
	{
		return (Long)(currentSession().createSQLQuery("select max(uid) MAXUID from t_global_user").addScalar("MAXUID", StandardBasicTypes.INTEGER)).uniqueResult();
	}

	public Page queryUserList(User user, Page page) {
		 
		DetachedCriteria criteria = mapingParam( user);
	    page.setDataList(getHibernateTemplate().findByCriteria(criteria,(page.getCurrentPage() - 1) * page.pageSize,page.pageSize));
	    
	    return page;
	}
	public int queryUserAmount(User model) {
		DetachedCriteria criteria = mapingParam(model);
		criteria.setProjection(Projections.rowCount());
		List list = getHibernateTemplate().findByCriteria(criteria);
		return Integer.valueOf(list.get(0).toString());
	}
	
	
	private DetachedCriteria mapingParam(User model)
	{
		DetachedCriteria criteria=DetachedCriteria.forClass(User.class);
	    if (StringUtils.isNotBlank(model.getAccount()))
	    {
	       criteria.add(Restrictions.like("account", model.getAccount(),MatchMode.ANYWHERE));
	    }
	    if (StringUtils.isNotBlank(model.getName()))
	    {
	       criteria.add(Restrictions.like("name",model.getName(),MatchMode.ANYWHERE));
	    }
	    if (StringUtils.isNotBlank(model.getGender()))
	    {
	       criteria.add(Restrictions.eq("gender",model.getGender()));
	    }
	    
	    if (model.getUid()!=null)
	    {
	       criteria.add(Restrictions.eq("uid",model.getUid()));
	    }
	    
	    if (StringUtils.isNotBlank(model.getTelephone()))
	    {
	       criteria.add(Restrictions.like("telephone", model.getTelephone(),MatchMode.ANYWHERE));
	    }
	    
	    if (StringUtils.isNotBlank(model.getEmail()))
	    {
	       criteria.add(Restrictions.like("email", model.getEmail(),MatchMode.ANYWHERE));
	    }
	    return criteria;
	 }
 
}
