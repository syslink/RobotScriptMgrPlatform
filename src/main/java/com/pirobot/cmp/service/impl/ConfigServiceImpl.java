/**
 * probject:cim
 *  
 * @version 2.0.0
 * @author 3979434@qq.com
 */
package com.pirobot.cmp.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pirobot.cmp.dao.impl.ConfigDaoImpl;
import com.pirobot.cmp.exception.IllegalExistCodeException;
import com.pirobot.cmp.exception.IllegalNullArgumentException;
import com.pirobot.cmp.model.Config;
import com.pirobot.cmp.service.ConfigService;
import com.pirobot.cmp.util.StringUtil;
@Service
public class ConfigServiceImpl implements ConfigService {
	@Autowired
	private ConfigDaoImpl configDao;

	public void setConfigDao(ConfigDaoImpl configDao) {
		this.configDao = configDao;
	}

	public Config queryById(String sequenceId) {
		return configDao.get(sequenceId);
	}

	public void save(Config config) {
		config.setGid(StringUtil.getUUID());
		if(StringUtils.isBlank(config.getDomain()) || StringUtils.isBlank(config.getKey()) || StringUtils.isBlank(config.getValue()))
		{
			throw new IllegalNullArgumentException();
		}
		if(configDao.querySingle(config)!=null)
		{
			throw new IllegalExistCodeException();
		}
		configDao.save(config);
	}

	@Override
	public List<Config> queryConfigByDomain(String domain) {
		return configDao.queryConfigByDomain(domain);
	}

	public void delete(String sequenceId) {
		Config config = new Config();
		config.setGid(sequenceId);
		configDao.delete(config);
	}

	@Override
	public void update(Config config) {
		if(configDao.hasExistent(config))
		{
			throw new IllegalExistCodeException();
		}
		configDao.update(config);
	}

	@Override
	public List<String> queryDomainList() {
		return configDao.queryDomainList();
	}

	@Override
	public Config querySingle(Config config) {
		// TODO Auto-generated method stub
		return configDao.querySingle(config);
	}

	@Override
	public List<Config> queryList(Config config) {
		// TODO Auto-generated method stub
		return configDao.queryList(config);
	}
}