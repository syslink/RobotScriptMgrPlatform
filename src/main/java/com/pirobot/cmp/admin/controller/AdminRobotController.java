 
package com.pirobot.cmp.admin.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pirobot.cmp.api.result.BaseResult;
import com.pirobot.cmp.model.Robot;
import com.pirobot.cmp.service.RobotService;
import com.pirobot.framework.web.Page;
@Controller    
@RequestMapping("/console/robot") 
public class AdminRobotController  {

	@Autowired
	private RobotService robotServiceImpl;


	public void setRobotServiceImpl(RobotService RobotServiceImpl) {
		this.robotServiceImpl = RobotServiceImpl;
	}

	  @RequestMapping(value="/list.action")  
	  public ModelAndView list(HttpServletRequest request, @ModelAttribute("robot") Robot robot,@ModelAttribute("page") Page page){ 
	
		  ModelAndView model = new ModelAndView();
		  
		  Cookie[] cookies = request.getCookies();//这样便可以获取一个cookie数组  
	      if (cookies != null && StringUtils.isEmpty(robot.getUid())) {
	          for(Cookie cookie : cookies){  
	              if(cookie.getName().equals("uid"))
	              {
	            	  robot.setUid(cookie.getValue());
	            	  robot.setAuthUid(cookie.getValue());
	            	  break;
	              }
	          }  
	      }  
	      
		  robotServiceImpl.queryPage(robot, page);
		  model.addObject("page", page);
		  model.setViewName("robot/manage");
		  return model;
		 
	}
	  
	 
	
	@RequestMapping(value="/update.action")  
	public @ResponseBody BaseResult update(Robot robot)   {

		BaseResult result = new BaseResult();
		 
		Robot target = robotServiceImpl.get(robot.getDeviceId());
		target.setName(robot.getName());
		robotServiceImpl.update(target);
		return result;
	}
	
	@RequestMapping(value="/delete.action")  
	public @ResponseBody BaseResult delete(String  deviceId)   {

		BaseResult result = new BaseResult();
		robotServiceImpl.delete(deviceId);
		return result;
	}
	 
}
