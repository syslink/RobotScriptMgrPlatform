/**
 * probject:cim
 *  
 * @version 2.0.0
 * @author 3979434@qq.com
 */
package com.pirobot.cmp.service.impl;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pirobot.framework.web.Page;
import com.pirobot.cmp.dao.impl.UserDaoImpl;
import com.pirobot.cmp.exception.IllegalUserAccountException;
import com.pirobot.cmp.model.User;
import com.pirobot.cmp.service.UserService;
@Service
public class UserServiceImpl implements UserService {

	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	private UserDaoImpl userDao;

	 
	public List<User> getUsers() {
		return userDao.getAll();
	}

	public User getByAccount(String account) {

		return userDao.queryByAccount(account);

	}

	public void update(User user) {
		userDao.update(user);
	}

	 
	public void setUserDao(UserDaoImpl userDao) {
		this.userDao = userDao;
	}

	@Override
	public void save(User user) throws Exception {
		
		try{
			userDao.save(user);
		}catch(Exception e){
			if(e instanceof SQLIntegrityConstraintViolationException){
				throw new IllegalUserAccountException();
			}else{
				throw e;
			}
		}
	}

	 

	@Override
	public void delete(String account) {
		User user = new User();
		user.setAccount(account);
		userDao.delete(user);
		
	}

	@Override
	public void queryUserList(User user, Page page) {
		int count = this.userDao.queryUserAmount(user);
		page.setCount(Integer.valueOf(count));
		if (page.getCount() == 0) {
			return;
		}
		this.userDao.queryUserList(user, page);
	}

	@Override
	public User get(Long uid) {
		return userDao.get(uid);
	}
	
	

}
