package com.pirobot.cmp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pirobot.cmp.dao.impl.ScriptCommandDaoImpl;
import com.pirobot.cmp.dao.impl.ScriptDaoImpl;
import com.pirobot.cmp.model.ScriptCommand;
import com.pirobot.cmp.service.ScriptCommandService;
import com.pirobot.cmp.util.StringUtil;
import com.pirobot.cmp.util.XunfeiService;

/**
 * 
 * @author 1968877693
 */
@Service
public class ScriptCommandServiceImpl implements ScriptCommandService {

    protected final Log log = LogFactory.getLog(getClass());
	@Resource
	private ScriptCommandDaoImpl scriptCommandDaoImpl;
	@Resource
	private ScriptDaoImpl scriptDao;

	
	public void setScriptDao(ScriptDaoImpl scriptDao) {
		this.scriptDao = scriptDao;
	}

	public void add(ScriptCommand command) {
		command.setCid(StringUtil.getUUID());
		command.setSort(System.currentTimeMillis()/1000);
		scriptCommandDaoImpl.save(command);
		scriptDao.updateRoleCount(command.getSid(), scriptCommandDaoImpl.queryRoleCount(command.getSid()));
	    
		beforehandTTS(command);
	}

	
	private void beforehandTTS(ScriptCommand command){
		
		if(command.getAction().equals("1")){
	    	JSONObject json = JSON.parseObject(command.getContent());
	    	String speaker = json.getString("speaker");
	    	String text = json.getString("text");
	    	String volume = json.getString("volume");
	    	String speed = json.getString("speed");
	    	String pitch = json.getString("pitch");
	    	new XunfeiService().tts(speaker, text, volume, speed, pitch);
	    }
		
	}
	public void delete(ScriptCommand command) {
		try{
			scriptCommandDaoImpl.updateParentId(command);
			scriptCommandDaoImpl.delete(command);
			scriptDao.updateRoleCount(command.getSid(), scriptCommandDaoImpl.queryRoleCount(command.getSid()));
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	public void update(ScriptCommand command)
	{
		scriptCommandDaoImpl.update(command);
		scriptDao.updateRoleCount(command.getSid(), scriptCommandDaoImpl.queryRoleCount(command.getSid()));
		beforehandTTS(command);
	}
 

	@Override
	public List<ScriptCommand> getCommandList(String sid) {
		return scriptCommandDaoImpl.queryList(sid);
	}

	@Override
	public ScriptCommand get(String gid) {
		// TODO Auto-generated method stub
		return scriptCommandDaoImpl.get(gid);
	}

	public void setScriptCommandDaoImpl(ScriptCommandDaoImpl scriptCommandDaoImpl) {
		this.scriptCommandDaoImpl = scriptCommandDaoImpl;
	}

	@Override
	public void saveBatch(List<ScriptCommand> commandList) {
		for(ScriptCommand command:commandList){
			if(command.getCid()==null){
				command.setCid(StringUtil.getUUID());
				command.setSort(System.currentTimeMillis()/1000);
				scriptCommandDaoImpl.save(command);
			}else
			{
				scriptCommandDaoImpl.update(command);
			}
			scriptCommandDaoImpl.saveOrUpdate(command);
			
			beforehandTTS(command);
		}
		scriptDao.updateRoleCount(commandList.get(0).getSid(), scriptCommandDaoImpl.queryRoleCount(commandList.get(0).getSid()));

	}
  
	 
	
}
