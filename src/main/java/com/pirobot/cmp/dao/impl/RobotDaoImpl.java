package com.pirobot.cmp.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.pirobot.cmp.model.Robot;
import com.pirobot.framework.web.Page;



/**
 * 
 * @author 1968877693
 */
@Repository
public class RobotDaoImpl extends  HibernateBaseDao<Robot> {

    protected final Log log = LogFactory.getLog(getClass());

	public void saveBindUID(String deviceId, String uid) {
		Session session = currentSession();
		String sql = "update Robot set uid = :uid where deviceId = :deviceId";
		Query query = session.createQuery(sql);
		query.setString("uid", uid);
		query.setString("deviceId", deviceId);
		query.executeUpdate();
	}

	public void saveUnbindUID(String deviceId, String uid) {
		// TODO Auto-generated method stub
		Session session = currentSession();
		String sql = "update Robot set uid = null where deviceId = :deviceId and uid = :uid";
		Query query = session.createQuery(sql);
		query.setString("uid", uid);
		query.setString("deviceId", deviceId);
		query.executeUpdate();
	}

	public List<Robot> queryAllListByUID(String uid) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Robot.class);
		criteria.add(Restrictions.or(Restrictions.eq("uid", uid), Restrictions.eq("authUid", uid))); 
        return (List<Robot>) getHibernateTemplate().findByCriteria(criteria);	
	}

	public List<Robot> queryOwnerListByUID(String uid) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Robot.class);
		criteria.add(Restrictions.eq("uid", uid));
        return (List<Robot>) getHibernateTemplate().findByCriteria(criteria);	
	}
	
	public List<Robot> queryAuthListByUID(String authUid) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Robot.class);
		criteria.add(Restrictions.eq("authUid", authUid));
        return (List<Robot>) getHibernateTemplate().findByCriteria(criteria);	
	}
	
	public Page queryPage(Robot robot, Page page) {
		 
		DetachedCriteria criteria = mapingParam( robot);
	    page.setDataList(getHibernateTemplate().findByCriteria(criteria,(page.getCurrentPage() - 1) * page.pageSize,page.pageSize));
	    
	    return page;
	}
	public int queryAmount(Robot model) {
		DetachedCriteria criteria = mapingParam(model);
		criteria.setProjection(Projections.rowCount());
		List list = getHibernateTemplate().findByCriteria(criteria);
		return Integer.valueOf(list.get(0).toString());
	}
	
	public int getMaxSn() {
		return (Integer)(currentSession().createSQLQuery("select max(sn) MAXSN from t_global_robot").addScalar("MAXSN", StandardBasicTypes.INTEGER)).uniqueResult();
//		
//		Query query = currentSession().createSQLQuery("select max(sn) from t_global_robot" );
//		List<String> list = query.list();
//		if(list == null || list.size() == 0)
//			return 9999;
//		String currentMaxSn = list.get(0);
//		return Integer.parseInt(currentMaxSn);
	}
	
	private DetachedCriteria mapingParam(Robot robot)
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(Robot.class);
		
	    if (robot.getSn() != null && robot.getSn() > 0)
	    {
	       criteria.add(Restrictions.eq("sn", robot.getSn()));
	    }
	    if (StringUtils.isNotBlank(robot.getDeviceId()))
	    {
	       criteria.add(Restrictions.like("deviceId",robot.getDeviceId(),MatchMode.ANYWHERE));
	    }
	    if (StringUtils.isNotBlank(robot.getState()))
	    {
	       criteria.add(Restrictions.eq("state", robot.getState()));
	    }
	    
	    if (robot.getUid() != null && robot.getAuthUid() != null)
	    {
	       criteria.add(Restrictions.or(Restrictions.eq("uid", robot.getUid()), Restrictions.eq("authUid", robot.getAuthUid()))); 
	    }
	    else if (robot.getUid() != null)
	    {
	    	criteria.add(Restrictions.eq("uid", robot.getUid()));
	    }
	    else if (robot.getAuthUid() != null)
	    {
	    	criteria.add(Restrictions.eq("authUid", robot.getAuthUid()));
	    }
	    return criteria;
	 }
 

}
