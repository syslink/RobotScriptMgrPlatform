/**
 * probject:cim
 * @version 2.0.0
 * 
 * @author 3979434@qq.com
 */ 
package com.pirobot.cmp.api.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pirobot.cmp.exception.IllegalExistCodeException;
import com.pirobot.cmp.exception.IllegalNullArgumentException;
import com.pirobot.cmp.model.Config;
import com.pirobot.cmp.service.ConfigService;
@Controller    
@RequestMapping("/cgi/config")  
public class ConfigController  {

	
	static Logger log = Logger.getLogger(ConfigController.class);
	@Autowired
	private ConfigService configServiceImpl;
	 
	public void setConfigServiceImpl(ConfigService configServiceImpl) {
		this.configServiceImpl = configServiceImpl;
	}


	@RequestMapping(value="/list.action")  
	public ModelAndView list(@ModelAttribute("config") Config config)  
	{
		ModelAndView model = new ModelAndView();
		model.addObject("list", configServiceImpl.queryList(config));
		model.setViewName("config/manage");
		return model;
	}
	@RequestMapping(value="/modify.action")  
	public @ResponseBody HashMap<String,Object> modify(Config config) throws IOException 
	{
		 HashMap<String,Object> datamap = new  HashMap<String,Object>();
		 
		 datamap.put("code", 200);
		 try{
		     configServiceImpl.update(config);
		 }catch(IllegalExistCodeException e)
		 {
			 datamap.put("code", 401);
		 }
		 return datamap;
	}
	@RequestMapping(value="/save.action")  
	public @ResponseBody HashMap<String,Object> save(Config config) throws IOException 
	{
		 HashMap<String,Object> datamap = new  HashMap<String,Object>();
		 
		 datamap.put("code", 200);
		 try{
		     configServiceImpl.save(config);
		 }catch(IllegalExistCodeException e)
		 {
			 datamap.put("code", 401);
		 }
		 catch(IllegalNullArgumentException e)
		 {
			 datamap.put("code", 403);
		 }
		 return datamap;
	}
	
	@RequestMapping(value="/delete.action")  
	public @ResponseBody HashMap<String,Object> delete(Config config) throws IOException 
	{
		 HashMap<String,Object> datamap = new  HashMap<String,Object>();
		 
		 datamap.put("code", 200);
		 configServiceImpl.delete(config.getGid());
		 return datamap;
	}
	
	
	@RequestMapping(value="/saveVersion.action")  
	public @ResponseBody HashMap<String,Object> saveVersion(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		 HashMap<String,Object> datamap = new  HashMap<String,Object>();
		 
		 datamap.put("code", 200);
		 String versionCode = request.getParameter("versionCode");
		 String versionName = request.getParameter("versionName");
		 String url = request.getParameter("url");
		 String description = request.getParameter("description");
		 Config config = new Config();
		 config.setDomain(request.getParameter("domain"));
		 try{
			 config.setKey("versionCode");
			 config.setValue(versionCode);
		     configServiceImpl.save(config);
		     
		     config.setKey("versionName");
			 config.setValue(versionName);
		     configServiceImpl.save(config);
		     
		     config.setKey("url");
			 config.setValue(url);
		     configServiceImpl.save(config);
		     
		     config.setKey("description");
			 config.setValue(description);
		     configServiceImpl.save(config);
		     
		 }catch(IllegalExistCodeException e)
		 {
			 datamap.put("code", 401);
		 }
		 catch(IllegalNullArgumentException e)
		 {
			 datamap.put("code", 403);
		 }
		return datamap;
	}

}
