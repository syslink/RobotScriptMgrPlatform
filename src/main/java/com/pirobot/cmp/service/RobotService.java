 
package com.pirobot.cmp.service;

import java.util.List;

import com.pirobot.cmp.model.Robot;
import com.pirobot.framework.web.Page;

 
public interface RobotService {

    public void add(Robot assist);
    public void delete(String devid);
    public List<Robot> getAllList(String uid);
    public List<Robot> getOwnerList(String uid);
    public List<Robot> getAuthList(String authUid);
    
    public Robot get(String devid);
    public void queryPage(Robot assist,Page page);
    public void update(Robot assist);

	public void saveBindUID(String deviceId, String uid);
	public void saveUnbindUID(String deviceId, String uid);
	public String saveAndRegister(Robot robot);
}
