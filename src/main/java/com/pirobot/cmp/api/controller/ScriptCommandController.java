/**
 * probject:cim
 * @version 2.0.0
 * 
 * @author 3979434@qq.com
 */ 
package com.pirobot.cmp.api.controller;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pirobot.cmp.api.result.BaseResult;
import com.pirobot.cmp.model.ScriptCommand;
import com.pirobot.cmp.service.ScriptCommandService;
@Controller    
@RequestMapping("/cgi/command")  
public class ScriptCommandController  {
	static Logger log = Logger.getLogger(ScriptCommandController.class);
 
	@Autowired
	private ScriptCommandService scriptCommandServiceImpl;

	public void setScriptCommandServiceImpl(
			ScriptCommandService scriptCommandServiceImpl) {
		this.scriptCommandServiceImpl = scriptCommandServiceImpl;
	}

	@RequestMapping(value="/list.api")  
	public @ResponseBody BaseResult   list(String sid) {
		BaseResult result = new BaseResult();
		List<ScriptCommand> cmdList = scriptCommandServiceImpl.getCommandList(sid);
		int totalCmdNum = cmdList.size();
		Map<Long, ScriptCommand> cmdMap = new HashMap<Long, ScriptCommand>();
		Map<Long, List<Long>> cmdTreeMap = new HashMap<Long, List<Long>>();
		cmdTreeMap.put(0L, new ArrayList<Long>());
		for(ScriptCommand cmd : cmdList)
		{
			Long sort = cmd.getSort();
			Long parent = cmd.getParent();
			cmdMap.put(sort, cmd);
			if(cmdTreeMap.containsKey(parent))
			{
				cmdTreeMap.get(parent).add(sort);
			}
			else
			{
				cmdTreeMap.put(parent, new ArrayList<Long>());
				cmdTreeMap.get(parent).add(sort);
			}
		}
		cmdList = new ArrayList<ScriptCommand>();
		List<Long> parentIdList = new ArrayList<Long>();
		parentIdList.add(0L);
		broadSearch(cmdMap, cmdTreeMap, parentIdList, cmdList);
		result.dataList = cmdList;
		return result;
	}
	private void broadSearch(Map<Long, ScriptCommand> cmdMap, Map<Long, List<Long>> cmdTreeMap, List<Long> parentIdList, List<ScriptCommand> cmdList)
	{
		log.info(parentIdList);
		List<Long> nextParentIdList = new ArrayList<Long>();
		for(Long id : parentIdList)
		{
			List<Long> subIdList = cmdTreeMap.get(id);
			if(subIdList != null && subIdList.size() > 0)
			{
				for(Long cid : subIdList)
				{
					cmdList.add(cmdMap.get(cid));
				}
				nextParentIdList.addAll(subIdList);
			}
		}
		if(nextParentIdList.size() > 0)
			broadSearch(cmdMap, cmdTreeMap, nextParentIdList, cmdList);
	}
	@RequestMapping(value="/add.api")  
	public @ResponseBody BaseResult add(ScriptCommand command){
		BaseResult result = new BaseResult();
		scriptCommandServiceImpl.add(command);
		result.data = command;
		return result;
	}

	@RequestMapping(value="/get.api")  
	public @ResponseBody BaseResult get(String cid ){
		BaseResult result = new BaseResult();
		result.data=scriptCommandServiceImpl.get(cid);
		return result;
	}
	
	@RequestMapping(value="/delete.api")  
	public @ResponseBody BaseResult delete(ScriptCommand command ){
		BaseResult result = new BaseResult();
		scriptCommandServiceImpl.delete(command);
		return result;
	}
	
	@RequestMapping(value="/update.api")  
	public @ResponseBody BaseResult update(ScriptCommand command ){
		BaseResult result = new BaseResult();
		ScriptCommand target = scriptCommandServiceImpl.get(command.getCid());
		target.setAction(command.getAction());
		target.setContent(command.getContent());
		target.setRole(command.getRole());
		target.setParent(command.getParent());
		target.setDelayTime(command.getDelayTime());
		target.setPrepareTime(command.getPrepareTime());
		scriptCommandServiceImpl.update(target);
		result.data=target;
		return result;
	}
	
	@RequestMapping(value="/batchSave.api")  
	public @ResponseBody BaseResult batchSave(@RequestBody List<ScriptCommand> commandList ){
		BaseResult result = new BaseResult();
		scriptCommandServiceImpl.saveBatch(commandList);
		return result;
	}
	
}
