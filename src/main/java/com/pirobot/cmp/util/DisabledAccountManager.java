/**
 * probject:cim
 * @version 2.0.0
 * 
 * @author 3979434@qq.com
 */ 
package com.pirobot.cmp.util;

import java.util.Properties;
 /**
  * 
  * 无效的用户账号管理,用于过滤系统保留关键字账号
  *
  */
public class DisabledAccountManager {

	private Properties config;

	private static DisabledAccountManager instance;

	private DisabledAccountManager() {
		loadConfig();
	}

	public synchronized static DisabledAccountManager getInstance() {
		if (instance == null) {
			instance = new DisabledAccountManager();
		}
		return instance;
	}

	public void loadConfig() {
		try {
			config = new Properties();
			config.load(DisabledAccountManager.class.getClassLoader().getResourceAsStream("disabled_account.properties"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public boolean contains(String key) {
		return config.containsKey(key);
	}

	public String get(String key) {
		return config.getProperty(key);
	}

}
