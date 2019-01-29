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
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.pirobot.cmp.model.Config;

@SuppressWarnings("unchecked")
@Repository
public class ConfigDaoImpl extends HibernateBaseDao<Config> {

	public List<Config> queryAllConfig() {
		DetachedCriteria criteria=DetachedCriteria.forClass(Config.class);
		return (List<Config>) getHibernateTemplate().findByCriteria(criteria);

	}

	public List<Config> queryConfigByDomain(String domain) {
		DetachedCriteria criteria=DetachedCriteria.forClass(Config.class);
		 criteria.add(Restrictions.eq("domain", domain));
		return (List<Config>) getHibernateTemplate().findByCriteria(criteria);

	}

	public List<String> queryDomainList() {
		String sql = "select domain from Config ";
		return (List<String>) getHibernateTemplate().find(sql);

	}

	public Config querySingle(Config config) {
		DetachedCriteria criteria=DetachedCriteria.forClass(Config.class);
		criteria.add(Restrictions.eq("domain", config.getDomain()));
		criteria.add(Restrictions.eq("key", config.getKey()));
		List<Config> list = (List<Config>) getHibernateTemplate().findByCriteria(criteria);
		return (list == null || list.isEmpty())?null:list.get(0);
	}

	public boolean  hasExistent(Config config) {
		DetachedCriteria criteria=DetachedCriteria.forClass(Config.class);
		criteria.add(Restrictions.eq("domain", config.getDomain()));
		criteria.add(Restrictions.eq("key", config.getKey()));
		criteria.add(Restrictions.ne("gid", config.getGid()));
		criteria.setProjection(Projections.rowCount());
		
		List list = getHibernateTemplate().findByCriteria(criteria);
		return Integer.valueOf(list.get(0).toString()) > 0;
	}
	
	public List<Config> queryList(Config config) {
		DetachedCriteria criteria=DetachedCriteria.forClass(Config.class);
		if(StringUtils.isNotEmpty(config.getDomain()))
		{
			criteria.add(Restrictions.eq("domain", config.getDomain()));
		}
		if(StringUtils.isNotEmpty(config.getKey()))
		{
			criteria.add(Restrictions.eq("key", config.getKey()));
		}
		return (List<Config>)getHibernateTemplate().findByCriteria(criteria);
	}
}