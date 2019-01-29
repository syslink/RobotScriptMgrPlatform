package com.pirobot.cmp.service.impl;

import java.util.List;
import java.util.TreeMap;

import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.pirobot.framework.web.Page;

import com.pirobot.cmp.dao.impl.ScriptCommandDaoImpl;
import com.pirobot.cmp.dao.impl.ScriptDaoImpl;
import com.pirobot.cmp.model.ScriptCommand;
import com.pirobot.cmp.model.Script;
import com.pirobot.cmp.service.ScriptService;
import com.pirobot.cmp.util.QAProcesser;
import com.pirobot.cmp.util.StringUtil;

/**
 * 
 * @author 1968877693
 */
@Service
public class ScriptServiceImpl implements ScriptService {

    protected final Log log = LogFactory.getLog(getClass());

	@Resource
	private ScriptDaoImpl scriptDao;
	@Resource
	private ScriptCommandDaoImpl commandDaoImpl;
		

	public void add(Script script) {
		script.setSid(StringUtil.getUUID());
		script.setTimestamp(System.currentTimeMillis());
		script.setRoleCount(0);
		scriptDao.save(script);
		saveOpenSearchDictionary(script);
	}

	public void delete(String scriptId) {
		Script script = scriptDao.get(scriptId);
		script.setSid(scriptId);
		commandDaoImpl.deleteBySID(scriptId);
		scriptDao.delete(script);
		
		delOpenSearchDictionary(script);
	}
	
	public void update(Script script)
	{
		scriptDao.update(script);
		saveOpenSearchDictionary(script);
	}

	public void setScriptDao(ScriptDaoImpl scriptDao) {
		this.scriptDao = scriptDao;
	}
 

	@Override
	public List<ScriptCommand> getCommandList(String sid) {
		// TODO Auto-generated method stub
		return commandDaoImpl.queryList(sid);
	}

	@Override
	public Page queryMePage(String uid, Page page) {
		page.setCount(scriptDao.queryAmount(uid));
		if (page.getCount() == 0) {
			return page;
		}
		return this.scriptDao.queryMePage(uid, page);
	}

	@Override
	public Page queryPage(Script script, Page page) {
		page.setCount(scriptDao.queryAmount(script));
		if (page.getCount() == 0) {
			return page;
		}
		return this.scriptDao.queryPage(script, page);
	}

	@Override
	public Script get(String sid) {
		return scriptDao.get(sid);
	}

	@Override
	public Script getFull(String sid) {
		Script model =  scriptDao.get(sid);
		model.setCommandList(commandDaoImpl.queryList(sid));
		return model;
	}
	
	@Override
	public Script getRandomFull(String uid, String type) {
		 
		Script model =  scriptDao.getRandomFull(uid, type);
		model.setCommandList(commandDaoImpl.queryList(model.getSid()));
		return model;
	}
	
	
	public void saveOpenSearchDictionary(Script script){
		try{
			QAProcesser.getInstance().addQA(script.getTitle(), "SID"+script.getSid());
		}catch(Exception e){}		
	}
	
	public void delOpenSearchDictionary(Script script){
		try{
			QAProcesser.getInstance().delQA(script.getTimestamp() + "");
		}catch(Exception e){}
	}

	@Override
	public  List<Script> querySelectList(String uid, Integer role,String type) {
		 
		return scriptDao.querySelectList(uid,role,type);
	}

	
	
}
