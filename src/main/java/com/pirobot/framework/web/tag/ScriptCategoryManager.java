/**
 * probject:cim
 * @version 2.0.0
 * 
 * @author 3979434@qq.com
 */ 
package com.pirobot.framework.web.tag;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
 
public class ScriptCategoryManager {

    private static final Log log = LogFactory.getLog(ScriptCategoryManager.class);

    private static Properties config;

    private static ScriptCategoryManager instance;

    private ScriptCategoryManager() {
        loadConfig();
    }

    public static ScriptCategoryManager getInstance() {
        if (instance == null) {
            synchronized (ScriptCategoryManager.class) {
                instance = new ScriptCategoryManager();
            }
        }
        return instance;
    }

    public void loadConfig() {
    	if(config==null)
    	{
	        try {
	        	config = new Properties();
	        	config.load(ScriptCategoryManager.class.getClassLoader().getResourceAsStream("script_category.properties"));
	        } catch (Exception ex) {
	            log.error(ex.getMessage(), ex);
	        }
    	}
    }
    
    public Properties getProperties() {
        return config;
    }
    
    public String getValue(String key){
    	return config.getProperty(key);
    }
}
