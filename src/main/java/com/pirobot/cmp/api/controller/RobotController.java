/**
 * probject:cim
 * @version 2.0.0
 * 
 * @author 3979434@qq.com
 */ 
package com.pirobot.cmp.api.controller;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pirobot.cmp.api.result.AnwerResult;
import com.pirobot.cmp.api.result.BaseResult;
import com.pirobot.cmp.api.result.XunfeiResult;
import com.pirobot.cmp.model.Robot;
import com.pirobot.cmp.model.RobotRecord;
import com.pirobot.cmp.service.RobotRecordService;
import com.pirobot.cmp.service.RobotService;
import com.pirobot.cmp.util.RobotQuestionUtils;
@CrossOrigin(origins = "http://scratch.cellbot.cn", maxAge = 3600)
@Controller    
@RequestMapping("/cgi/robot")  
public class RobotController  {
	@Autowired
	private RobotService robotServiceImpl;
	static final ExecutorService  executor   = Executors.newFixedThreadPool(4);

	@Autowired
	private RobotRecordService robotRecordServiceImpl;
	protected final static Logger logger = Logger.getLogger(RobotController.class);

	
	public void setRobotRecordServiceImpl(RobotRecordService robotRecordServiceImpl) {
		this.robotRecordServiceImpl = robotRecordServiceImpl;
	}

	public void setRobotServiceImpl(RobotService robotServiceImpl) {
		this.robotServiceImpl = robotServiceImpl;
	}

	@RequestMapping(value="/add.api")  
	public @ResponseBody BaseResult   add(Robot robot) {
		BaseResult result = new BaseResult();
		robotServiceImpl.add(robot);
		return result;
	}

	@RequestMapping(value="/update.api")  
	public @ResponseBody BaseResult   update(Robot robot) {
		BaseResult result = new BaseResult();
		robotServiceImpl.update(robot);
		return result;
	}
	
	@RequestMapping(value="/list.api")  
	public @ResponseBody BaseResult   list(String uid) {
		BaseResult result = new BaseResult();
		result.dataList = robotServiceImpl.getAllList(uid);
		return result;
	}
	
	@RequestMapping(value="/ownerList.api")  
	public @ResponseBody BaseResult   ownerList(String uid) {
		BaseResult result = new BaseResult();
		result.dataList = robotServiceImpl.getOwnerList(uid);
		return result;
	}

	@RequestMapping(value="/authList.api")  
	public @ResponseBody BaseResult   authList(String authUid) {
		BaseResult result = new BaseResult();
		result.dataList = robotServiceImpl.getAuthList(authUid);
		return result;
	}
	
	@RequestMapping(value="/detail.api")  
	public @ResponseBody BaseResult  detail(String deviceId) {
		BaseResult result = new BaseResult();
		result.data = JSONObject.toJSONString(robotServiceImpl.get(deviceId));
		return result;
	}

	@RequestMapping(value="/bind.api")  
	public @ResponseBody BaseResult bind(String uid,String deviceId){
		robotServiceImpl.saveBindUID(deviceId,uid);	
		BaseResult result = new BaseResult();
		return result;
	}
	

	@RequestMapping(value="/register.api")  
	public @ResponseBody BaseResult register(Robot robot){
		
		BaseResult result = new BaseResult();
		try{
			result.data = robotServiceImpl.saveAndRegister(robot);
		}catch(Exception e){}
		return result;
	}
	
	
	@RequestMapping(value="/question.api")  
	public @ResponseBody AnwerResult question(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		 logger.warn("开始处理问题..............");
		 final String deviceId = request.getParameter("deviceId");  // 待回答的机器人账号
		 final String sender = request.getParameter("uid");  // 提问者信息，如果是语音交流，another=voiceCommunication，如果是文字交流，则是对方账号
		 final String content =request.getParameter("content"); 
		 final String city =request.getParameter("city"); 
		 
		 final String action =request.getParameter("action"); 
		 AnwerResult anwerResult =RobotQuestionUtils.question(deviceId, sender, content, action);
		 
		 //对于讯飞天气数据的特殊处理---------------begin-------------------
		 if(anwerResult.source == "2"){
			 com.alibaba.fastjson.JSONObject data = JSON.parseObject(anwerResult.data.toString());
			 String service = data.getString("service");
			 if(service.equalsIgnoreCase("weather")){
				 String locType = data.getJSONObject("semantic").getJSONObject("slots").getJSONObject("location").getString("type");
				 if("LOC_POI".equalsIgnoreCase(locType)){
					 anwerResult =RobotQuestionUtils.question(deviceId, sender, city+content, anwerResult.source); 
					 data = JSON.parseObject(anwerResult.data.toString());
				 }
				 XunfeiResult xunfeiResult = new XunfeiResult();
				 xunfeiResult.data = data.getJSONObject("data").getJSONArray("result").getJSONObject(1).toJSONString();
				 xunfeiResult.service = service;
				 anwerResult.data = JSON.toJSONString(xunfeiResult);
			 }
		 }
		 //对于讯飞天气数据的特殊处理---------------end-------------------
		 
		 String anwer = anwerResult.data;
		 String source = anwerResult.source;
		 
		 executor.execute(new Runnable(){
			 public void run(){
				 
				 String fullSentence = content;//ContextManager.getInstance().processContextInfo(sender, content);
				 final RobotRecord record = new RobotRecord();
				 record.setQuestion(content);
				 record.setFixedQuestion(fullSentence);
				 record.setAnswer(anwer);
				 record.setRid(deviceId);
				 record.setSource(source);
				 record.setUid(sender);
				 
				 robotRecordServiceImpl.add(record);;
			 }
		 });
		 
		 logger.warn("问题处理完毕,返回数据..............");
		 return anwerResult;
	}
}
