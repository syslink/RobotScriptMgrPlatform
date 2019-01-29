/**
 * probject:cim
 * @version 2.0.0
 * 
 * @author 3979434@qq.com
 */ 
package com.pirobot.cmp.admin.controller;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pirobot.cmp.api.result.BaseResult;
import com.pirobot.cmp.model.ScriptCommand;
import com.pirobot.cmp.service.ScriptCommandService;
@Controller()    
@RequestMapping("/console/command")  
public class AdminScriptCommandController  {
 
	@Autowired
	private ScriptCommandService scriptCommandServiceImpl;

	public void setScriptCommandServiceImpl(
			ScriptCommandService scriptCommandServiceImpl) {
		this.scriptCommandServiceImpl = scriptCommandServiceImpl;
	}

	@RequestMapping(value="/list.action")  
	public @ResponseBody BaseResult   list(String sid) {
		BaseResult result = new BaseResult();
		List<ScriptCommand> cmdList = scriptCommandServiceImpl.getCommandList(sid);
		//int totalCmdNum = cmdList.size();
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
	@RequestMapping(value="/add.action")  
	public @ResponseBody BaseResult add(ScriptCommand command){
		BaseResult result = new BaseResult();
		scriptCommandServiceImpl.add(command);
		result.data = command;
		return result;
	}

	@RequestMapping(value="/get.action")  
	public @ResponseBody BaseResult get(String cid ){
		BaseResult result = new BaseResult();
		result.data=scriptCommandServiceImpl.get(cid);
		return result;
	}
	
	@RequestMapping(value="/delete.action")  
	public @ResponseBody BaseResult delete(ScriptCommand command ){
		BaseResult result = new BaseResult();
		scriptCommandServiceImpl.delete(command);
		return result;
	}
	
	@RequestMapping(value="/update.action")  
	public @ResponseBody BaseResult update(ScriptCommand command ){
		BaseResult result = new BaseResult();
		ScriptCommand target = scriptCommandServiceImpl.get(command.getCid());
		target.setContent(command.getContent());
		target.setRole(command.getRole());
		target.setParent(command.getParent());
		target.setDelayTime(command.getDelayTime());
		target.setPrepareTime(command.getPrepareTime());
		target.setAction(command.getAction());
		scriptCommandServiceImpl.update(target);
		result.data = target;
		return result;
	}
}
