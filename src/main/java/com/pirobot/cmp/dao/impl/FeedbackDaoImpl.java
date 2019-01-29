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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.pirobot.framework.web.Page;
import com.pirobot.cmp.model.Feedback;

@SuppressWarnings("unchecked")
@Repository
public class FeedbackDaoImpl extends HibernateBaseDao<Feedback>

{

	public Page queryByPage(Feedback feedBack, Page page) {
		DetachedCriteria criteria = mapingParam(feedBack);
		criteria.addOrder(Order.desc("timestamp"));
		page.setDataList(getHibernateTemplate().findByCriteria(criteria,(page.getCurrentPage() - 1) * page.pageSize,page.pageSize));
		return page;
	}

 

	 

	public int queryAmount(Feedback feedBack) {
		DetachedCriteria criteria = mapingParam(feedBack);
		criteria.setProjection(Projections.rowCount());
		List list = getHibernateTemplate().findByCriteria(criteria);
		return Integer.valueOf(list.get(0).toString());
	}

	 
	
	private DetachedCriteria mapingParam(Feedback feedBack) {
		DetachedCriteria criteria=DetachedCriteria.forClass(Feedback.class);

		if (!StringUtils.isEmpty(feedBack.getAccount())) {
			criteria.add(Restrictions.eq("account", feedBack.getAccount()));
		}
		if (!StringUtils.isEmpty(feedBack.getAppVersion())) {
			criteria.add(Restrictions.eq("appVersion", feedBack.getAppVersion()));
		}
		if (!StringUtils.isEmpty(feedBack.getSdkVersion())) {
			criteria.add(Restrictions.eq("sdkVersion", feedBack.getSdkVersion()));
		}
		return criteria;
	}

 
	 
}