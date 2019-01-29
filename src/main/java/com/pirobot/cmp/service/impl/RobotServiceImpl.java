package com.pirobot.cmp.service.impl;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.pirobot.cmp.dao.impl.RobotDaoImpl;
import com.pirobot.cmp.model.Robot;
import com.pirobot.cmp.service.RobotService;
import com.pirobot.framework.web.Page;



/**
 * 
 * @author 1968877693
 */
@Service
public class RobotServiceImpl implements RobotService {

	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	private RobotDaoImpl robotDaoImpl;
	
	public void add(Robot assist) {
		robotDaoImpl.save(assist);
	}

	@Override
	public List<Robot> getAllList(String uid) {
		return robotDaoImpl.queryAllListByUID(uid);
	}
	@Override
	public List<Robot> getOwnerList(String uid) {
		return robotDaoImpl.queryOwnerListByUID(uid);
	}
	public List<Robot> getAuthList(String authUid)
	{
		return robotDaoImpl.queryAuthListByUID(authUid);
	}
	@Override
	public void update(Robot assist) {
		robotDaoImpl.update(assist);
	}

	@Override
	public void saveBindUID(String deviceId, String uid) {
		robotDaoImpl.saveBindUID(deviceId,uid);
	}

	public void setRobotDaoImpl(RobotDaoImpl robotDaoImpl) {
		this.robotDaoImpl = robotDaoImpl;
	}

	@Override
	public void saveUnbindUID(String deviceId, String uid) {
		robotDaoImpl.saveUnbindUID(deviceId,uid);
	}

	@Override
	public void queryPage(Robot assist, Page page) {
		int count = this.robotDaoImpl.queryAmount(assist);
		page.setCount(Integer.valueOf(count));
		if (page.getCount() == 0) {
			return;
		}
		this.robotDaoImpl.queryPage(assist, page);
	}

	@Override
	public Robot get(String devid) {
		// TODO Auto-generated method stub
		return robotDaoImpl.get(devid);
	}

	@Override
	public void delete(String devid) {
		// TODO Auto-generated method stub
		Robot target = new Robot();
		target.setDeviceId(devid);
		robotDaoImpl.delete(target);
	}





	@Override
	public synchronized String saveAndRegister(Robot robot) {
		Robot target = robotDaoImpl.get(robot.getDeviceId());
		JSONObject robotInfo = new JSONObject();
		if(target != null)
		{
			String sn = "0";
			Integer snInt = target.getSn(); 
			String name = target.getName();
			if(snInt != null)
			{
				sn = snInt.toString();
				robotInfo.put("sn", sn);
			}
			robotInfo.put("name", name != null ? name : "");
		}
		else
		{
			int sn = robotDaoImpl.getMaxSn() + 1;
			robot.setSn(sn);
			robotDaoImpl.save(robot);
			robotInfo.put("sn", sn);
			robotInfo.put("name", "");
		}
		return robotInfo.toJSONString();
		
	}
 
}
