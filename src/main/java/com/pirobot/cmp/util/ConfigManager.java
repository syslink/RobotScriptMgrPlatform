/**
 * probject:cim
 * @version 2.0.0
 * 
 * @author 3979434@qq.com
 */ 
package com.pirobot.cmp.util;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
 
public class ConfigManager {

    private static final Log log = LogFactory.getLog(ConfigManager.class);

    private static Properties config;

    private static ConfigManager instance;

    private ConfigManager() {
        loadConfig();
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            synchronized (ConfigManager.class) {
                instance = new ConfigManager();
            }
        }
        return instance;
    }

    public void loadConfig() {
    	if(config==null)
    	{
	        try {
	        	config = new Properties();
	        	config.load(ConfigManager.class.getClassLoader().getResourceAsStream("config.properties"));
	        } catch (Exception ex) {
	            log.error(ex.getMessage(), ex);
	        }
    	}
    }
    
    public String getStringValue(String key) {
        return config.getProperty(key);
    }
    
    public int getIntValue(String key) {
    	try{
            return  Integer.parseInt(getStringValue(key).trim());
    	}catch(Exception e)
    	{
    		return 0;
    	}
    }
}
