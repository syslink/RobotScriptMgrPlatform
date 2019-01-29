/**
 * probject:cim
 * @version 2.0.0
 * 
 * @author 3979434@qq.com
 */ 
package com.pirobot.framework.web.interceptor;

import java.util.Properties;
 
public class CustomKeyManager {

	private Properties config;

	private static CustomKeyManager instance;

	private CustomKeyManager() {
		loadConfig();
	}

	public synchronized static CustomKeyManager getInstance() {
		if (instance == null) {
			instance = new CustomKeyManager();
		}
		return instance;
	}

	public void loadConfig() {
		try {
			config = new Properties();
			config.load(CustomKeyManager.class.getClassLoader()
					.getResourceAsStream("apikeys.properties"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public boolean isapproved(String key) {
		if (key == null) {
			return false;
		}
		return config.containsKey(key);
	}

	public String get(String key) {
		return config.getProperty(key);
	}

}
