/**
 * probject:cim
 * @version 2.0.0
 * 
 * @author 3979434@qq.com
 */ 
package com.pirobot.cmp.api.controller;

import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.pirobot.cmp.api.result.BaseResult;
import com.pirobot.cmp.model.User;
import com.pirobot.cmp.service.UserService;
@Controller    
@RequestMapping("/cgi/user")
public class UserController{

 
	@Autowired
	private UserService userServiceImpl;

	
	 
	@RequestMapping(value="/login.api")  
	public @ResponseBody BaseResult login(User user){  
		
		String password = user.getPassword();
		BaseResult result = new BaseResult();
		
		user = userServiceImpl.getByAccount(user.getAccount());
		
		if(user==null)
		{
			result.code=404;
		} else{
			if(user.getPassword().equalsIgnoreCase(password))
			{
				result.data = user;
			}
			else
			{
				result.code=403;
			}
		}
		return result;
	}



    @RequestMapping(value="/register.api")  
	public @ResponseBody BaseResult register(User user) throws SQLException{  

    	BaseResult result = new BaseResult();
		try{
			userServiceImpl.save(user);
			result.data = user.getUid();
			
		}catch(Exception e){
			result.code= 101;
		} 
		return result;
	}
	
	public void setUserServiceImpl(UserService userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}
 
}
