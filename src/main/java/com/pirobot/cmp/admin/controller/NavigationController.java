/**
 * probject:cim
 * @version 2.0.0
 * 
 * @author 3979434@qq.com
 */ 
package com.pirobot.cmp.admin.controller;




import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller()    
@RequestMapping("/console")  
public class NavigationController  
{
  	 
    @RequestMapping(value="/index.action",method=RequestMethod.GET)  
  	public ModelAndView console(ModelAndView  model) throws Exception {
    	model.setViewName("index");
  		return model;
  	}
}