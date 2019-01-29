package com.pirobot.cmp.admin.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pirobot.cmp.util.ConfigManager;

@Controller    
@RequestMapping("/system") 
public class SystemController  
{

      
      @RequestMapping(value="/login.do",method=RequestMethod.POST)  
  	  public @ResponseBody int login(HttpServletRequest request, HttpServletResponse response) throws Exception {

  		String password = request.getParameter("password");
  		String account = request.getParameter("account");

  		if (account.equalsIgnoreCase("system") && password.equalsIgnoreCase("system")) {
  			 
  			request.getSession().setAttribute("manager", "Admin");
  			return 200;
  		} else {
  			return 403;
  		}

  	}

}