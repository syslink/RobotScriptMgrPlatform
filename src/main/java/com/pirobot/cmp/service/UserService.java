/**
 * probject:cim
 *  
 * @version 2.0.0
 * @author 3979434@qq.com
 */ 
package com.pirobot.cmp.service;


import com.pirobot.framework.web.Page;
import com.pirobot.cmp.model.User;

public interface UserService {

    public void update(User user);
    

    public User get(Long uid);
    
    public User getByAccount(String account);

	public void save(User user) throws Exception;

	
	public void queryUserList(User user, Page page);

	public void delete(String account);
	
	
}
