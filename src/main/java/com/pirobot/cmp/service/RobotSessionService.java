/**
 * probject:cim
 *  
 * @version 2.0.0
 * @author 3979434@qq.com
 */
package com.pirobot.cmp.service;

import java.util.List;

import com.pirobot.framework.web.Page;
import com.pirobot.cmp.model.Robot;
 
public  interface RobotSessionService
{
	  public  List<Robot> queryList(String[] deviceIds);
	  public  Page queryByPage(Robot  Robot, Page page);
}