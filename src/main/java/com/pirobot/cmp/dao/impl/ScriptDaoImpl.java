package com.pirobot.cmp.dao.impl;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.pirobot.framework.web.Page;
import com.pirobot.cmp.model.Script;
 

/**
 * 
 * @author 1968877693
 */
@Repository
public class ScriptDaoImpl extends  HibernateBaseDao<Script>  {


	public Page queryPage(Script script, Page page) {
		DetachedCriteria criteria = mapingParam(script);
		page.setDataList(this.getHibernateTemplate().findByCriteria(criteria,(page.getCurrentPage() - 1) * page.pageSize, page.pageSize));
		return page;
	}
	
	public Page queryMePage(String uid,Page page) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Script.class);
		criteria.add(Restrictions.eq("uid", uid));
		page.setDataList(getHibernateTemplate().findByCriteria(criteria,(page.getCurrentPage() - 1) * page.pageSize, page.pageSize));
	    return page;
	}
	
	
	public void updateRoleCount(String sid,int count) {
		Session session = currentSession();
		String sql = "update   Script set roleCount = :count   where sid = :sid";
		Query query = session.createQuery(sql);
		query.setParameter("sid", sid);
		query.setParameter("count", count);
		query.executeUpdate();
		
		
	}
	
	
	public int queryAmount(Script script) {
		DetachedCriteria criteria = mapingParam(script);
		criteria.setProjection(Projections.rowCount());
		List list = getHibernateTemplate().findByCriteria(criteria);
		return Integer.valueOf(list.get(0).toString());
	}
	public int queryAmount(String uid) {
		DetachedCriteria criteria=DetachedCriteria.forClass(Script.class);
		criteria.add(Restrictions.eq("uid",uid));
		criteria.setProjection(Projections.rowCount());
		List list = getHibernateTemplate().findByCriteria(criteria);
		return Integer.valueOf(list.get(0).toString());
	}
	

	public Script getRandomFull(String uid, String type) {
		Script script = new Script();
		script.setType(type);
		int count = queryAmount(script);
		int index = new Random().nextInt(count);
		Session session = currentSession();
		String sql = "SELECT * FROM "+Script.TABLE_NAME+"  where uid=:uid and type=:type LIMIT "+index+",1";
		if(type.isEmpty())
			sql = "SELECT * FROM "+Script.TABLE_NAME+"  where uid=:uid LIMIT "+index+",1";
		SQLQuery query = session.createSQLQuery(sql);
		query.setParameter("uid", uid);
		if(!type.isEmpty())
			query.setParameter("type", type);
		query.addEntity(Script.class);
		List<Script> list = query.list();
		return (list==null || list.isEmpty())?null:list.get(0);
	}
	
	public List<Script> querySelectList(String uid,Integer role,String type) {
		DetachedCriteria criteria=DetachedCriteria.forClass(Script.class);
		criteria.add(Restrictions.or(Restrictions.eq("uid",uid), Restrictions.eq("open", 1)));
		if(role!=null){
			criteria.add(Restrictions.eq("roleCount",role));
		}
		if (StringUtils.isNotBlank(type)) {
			criteria.add(Restrictions.eq("type",type));
		}
		return (List<Script>) getHibernateTemplate().findByCriteria(criteria);
	}

	
	
	
	private DetachedCriteria mapingParam(Script script) {
		DetachedCriteria criteria=DetachedCriteria.forClass(Script.class);

		if (StringUtils.isNotBlank(script.getUid())) {
			criteria.add(Restrictions.eq("uid",script.getUid()));
		}
		if (StringUtils.isNotBlank(script.getSid())) {
			criteria.add(Restrictions.eq("sid",script.getSid()));
		}
		if (StringUtils.isNotBlank(script.getState())) {
			criteria.add(Restrictions.eq("state",script.getState()));
		}
		if (StringUtils.isNotBlank(script.getTitle())) {
			criteria.add(Restrictions.like("title",script.getTitle(), MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotBlank(script.getType())) {
			criteria.add(Restrictions.eq("type",script.getType()));
		}
		if (script.getRoleCount()!=null) {
			criteria.add(Restrictions.eq("roleCount", script.getRoleCount()));
		}
		if (script.getOpen()!=null) {
			criteria.add(Restrictions.eq("open", script.getOpen()));
		}
		
		criteria.addOrder(Order.desc("timestamp"));
		return criteria;
	}


}
