/**
 * probject:cim
 * @version 2.0.0
 * 
 * @author 3979434@qq.com
 */ 
package com.pirobot.cmp.admin.controller;



import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pirobot.framework.web.Page;
import com.pirobot.cmp.api.result.BaseResult;
import com.pirobot.cmp.model.Script;
import com.pirobot.cmp.service.ScriptService;
import com.pirobot.cmp.util.Constants;
@Controller()    
@RequestMapping("/console/script")  
public class AdminScriptController  {
 
	@Autowired
	private ScriptService scriptServiceImpl;

	
	public void setScriptServiceImpl(ScriptService scriptServiceImpl) {
		this.scriptServiceImpl = scriptServiceImpl;
	}

	 @RequestMapping(value="/list.action")  
	  public ModelAndView list(HttpServletRequest request, Script script ,Page page) {  

		  Cookie[] cookies = request.getCookies();//这样便可以获取一个cookie数组  
          if (cookies != null && StringUtils.isEmpty(script.getUid())) {
             for(Cookie cookie : cookies){  
                 if(cookie.getName().equals("uid"))
                 {
                	 script.setUid(cookie.getValue());
               	  	 break;
                 }
             }  
          }  
		  scriptServiceImpl.queryPage(script, page);
		
		  ModelAndView model = new ModelAndView();
		  model.setViewName("script/manage");
		  return model;
		  
	}
	
	@RequestMapping(value="/add.action")  
		public @ResponseBody BaseResult  add(Script script )   {

		    BaseResult result = new BaseResult();
		    script.setState(Script.STATUS_NORMAL);
			scriptServiceImpl.add(script);
			return result;
	}
	 
	@RequestMapping(value="/update.action")  
	public @ResponseBody BaseResult  update(Script script )   {

	    BaseResult result = new BaseResult();
	    Script target = scriptServiceImpl.get(script.getSid());
	    target.setOpen(script.getOpen());
	    target.setTitle(script.getTitle());
	    target.setType(script.getType());
	    target.setDescription(script.getDescription());
		scriptServiceImpl.update(target);
		return result;
}
	
	@RequestMapping(value="/delete.action")  
	public @ResponseBody BaseResult delete( String sid)   {
		BaseResult result = new BaseResult();
		scriptServiceImpl.delete(sid);
		return result;
	}
	
	
	@RequestMapping(value="/get.action")  
	public @ResponseBody BaseResult get(String sid ){
		BaseResult result = new BaseResult();
		result.data=scriptServiceImpl.get(sid);
		return result;
	}
}
