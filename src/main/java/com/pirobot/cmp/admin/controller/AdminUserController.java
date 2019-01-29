 
package com.pirobot.cmp.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pirobot.cmp.api.result.BaseResult;
import com.pirobot.cmp.model.User;
import com.pirobot.cmp.service.UserService;
import com.pirobot.cmp.util.ConfigManager;
import com.pirobot.framework.web.Page;
@Controller    
@RequestMapping("/console/user") 
public class AdminUserController  {

	@Autowired
	private UserService userServiceImpl;


	public void setUserServiceImpl(UserService UserServiceImpl) {
		this.userServiceImpl = UserServiceImpl;
	}

    @RequestMapping(value="/register.action",method=RequestMethod.POST)  
	public @ResponseBody BaseResult register(User user, String inviteCode) {
		BaseResult result = new BaseResult();
    	String inviteCodes = ConfigManager.getInstance().getStringValue("INVITE_CODE");
    	if(!inviteCodes.contains(inviteCode))
    	{
    		result.code = 401;
    		return result;
    	}
    	try{
			userServiceImpl.save(user);
		}catch(Exception e){
			result.code = 402;
		} 
    	return result;
	}

    @RequestMapping(value="/login.action",method=RequestMethod.POST)  
	public @ResponseBody BaseResult login(User user) {
		BaseResult result = new BaseResult();
		String password = user.getPassword();
		
		user = userServiceImpl.getByAccount(user.getAccount());
		
		if(user == null)
		{
			result.code=404;
		} 
		else
		{
			if(user.getPassword().equalsIgnoreCase(password))
			{
				result.data = user.getUid();
			}else
			{
				result.code=403;
			}
		}
    	return result;
	}
	
    
	  @RequestMapping(value="/list.action")  
	  public ModelAndView list(User user,Page page){ 

		  ModelAndView model = new ModelAndView();
		  userServiceImpl.queryUserList(user, page);
  		  model.setViewName("user/manage");
  		  return model;
		 
	}
	  
	 
	
	@RequestMapping(value="/update.action")  
	public @ResponseBody BaseResult update(User user)   {

		BaseResult result = new BaseResult();
		 
		User target = userServiceImpl.get(user.getUid());
		target.setName(user.getName());
		target.setEmail(user.getEmail());
		target.setTelephone(user.getTelephone());
		target.setGender(user.getGender());
		
		userServiceImpl.update(target);
		return result;
	}

	 
	
	
	@RequestMapping(value="/delete.action")  
	public @ResponseBody BaseResult delete(String uid)   {
		BaseResult result = new BaseResult();
		userServiceImpl.delete(uid);
		return result;
	}
	 
}
