/**
 * probject:cim
 * @version 2.0.0
 * 
 * @author 3979434@qq.com
 */ 
package com.pirobot.framework.container;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;

import com.pirobot.cmp.util.CustomActionsManager;
import com.pirobot.cmp.util.QAProcesser;


 
public class ContextLoaderListener  implements ServletContextListener{

	ContextLoader contextLoader;
    protected final Logger logger = Logger.getLogger(ContextLoaderListener.class); 
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		 if(contextLoader != null)
	            contextLoader.closeWebApplicationContext(event.getServletContext());
		 ContextHolder.setContext( null);
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		 
		if(contextLoader == null){
			contextLoader = new ContextLoader();
		}
        
		logger.debug("******************* container start begin ******************************");
		ContextHolder.setContext(contextLoader.initWebApplicationContext(event.getServletContext()));
		logger.debug("******************* container start successfull ************************");
		
		
		new Thread(new Runnable() {			
			@Override
			public void run() {
				QAProcesser.getInstance().login();
				CustomActionsManager.getInstance().importRule("customactions.xml");
				CustomActionsManager.getInstance().initRuleSamentic();
			}
		}).start();
		
	}
	   
		
}
