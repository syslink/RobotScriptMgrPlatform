package com.pirobot.cmp.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.pirobot.cmp.model.ScriptCommand;

 
@Repository
public class ScriptCommandDaoImpl extends  HibernateBaseDao<ScriptCommand> {

    protected final Log log = LogFactory.getLog(getClass());

    public List<ScriptCommand> queryList(String sid) {
    	DetachedCriteria criteria = DetachedCriteria.forClass(ScriptCommand.class);
		criteria.addOrder(Order.asc("sort"));
		criteria.add(Restrictions.eq("sid", sid));
		return (List<ScriptCommand>) getHibernateTemplate().findByCriteria(criteria);
	}
    
    public int queryRoleCount(String sid){
    	DetachedCriteria criteria = DetachedCriteria.forClass(ScriptCommand.class);
    	criteria.add(Restrictions.eq("sid", sid));
    	ProjectionList projList = Projections.projectionList();  
    	projList.add(Projections.groupProperty("role"));
    	projList.add(Projections.rowCount());
    	criteria.setProjection(projList);
    	List<String> list = (List<String>)getHibernateTemplate().findByCriteria(criteria);
    		
    	return list.size();
    }
    
    public void deleteBySID(String sid) {
		Session session = currentSession();
		String sql = "delete  ScriptCommand    where sid = :sid";
		Query query = session.createQuery(sql);
		query.setParameter("sid", sid);
		query.executeUpdate();
		
	}
	public void updateParentId(ScriptCommand cmd)
	{
		Session session = currentSession();
		String sql = "update ScriptCommand set parent=:newParent where sid = :sid and parent=:oldParent";
		Query query = session.createQuery(sql);
		query.setParameter("sid", cmd.getSid());
		query.setParameter("newParent", cmd.getParent());
		query.setParameter("oldParent", cmd.getSort());
		query.executeUpdate();
	}
}
