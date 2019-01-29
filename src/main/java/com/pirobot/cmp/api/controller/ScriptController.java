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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.pirobot.cmp.api.result.BaseResult;
import com.pirobot.cmp.model.Script;
import com.pirobot.cmp.model.ScriptCommand;
import com.pirobot.cmp.service.ScriptService;
import com.pirobot.cmp.util.ConfigManager;
import com.pirobot.cmp.util.HttpPostUtils;
import com.pirobot.framework.web.Page;
@Controller    
@RequestMapping("/cgi/script")  
public class ScriptController  {
 
	@Autowired
	private ScriptService scriptServiceImpl;

	
	public void setScriptServiceImpl(ScriptService scriptServiceImpl) {
		this.scriptServiceImpl = scriptServiceImpl;
	}

	@RequestMapping(value="/list.api")  
	public @ResponseBody BaseResult   list(String uid,Page page) {
		BaseResult result = new BaseResult();
		scriptServiceImpl.queryMePage(uid, page);
		result.dataList=page.getDataList();
		result.page=page.toHashMap();
		return result;
	}
	
	
	
	@RequestMapping(value="/search.api")  
	public @ResponseBody BaseResult   search(Script script,Page page) {
		BaseResult result = new BaseResult();
		script.setOpen(Script.STATUS_OPEN);
		scriptServiceImpl.queryPage(script, page);
		result.dataList=page.getDataList();
		result.page=page.toHashMap();
		return result;
	}

	@RequestMapping(value="/add.api")  
	public @ResponseBody BaseResult add(Script script){
		BaseResult result = new BaseResult();
		scriptServiceImpl.add(script);
		result.data = script.getSid();
		return result;
	}

	@RequestMapping(value="/update.api")  
	public @ResponseBody BaseResult update(Script script){
		 BaseResult result = new BaseResult();
		 Script target = scriptServiceImpl.get(script.getSid());
		 target.setOpen(script.getOpen());
		 target.setTitle(script.getTitle());
		 target.setType(script.getType());
		 target.setDescription(script.getDescription());
		 target.setState(Script.STATUS_NORMAL);
	     scriptServiceImpl.update(target);
		 return result;
	}
	
	@RequestMapping(value="/delete.api")  
	public @ResponseBody BaseResult delete( String sid)   {
		BaseResult result = new BaseResult();
		scriptServiceImpl.delete(sid);
		return result;
	}
	
	@RequestMapping(value="/get.api")  
	public @ResponseBody BaseResult get(String sid ){
		BaseResult result = new BaseResult();
		result.data=scriptServiceImpl.getFull(sid);
		return result;
	}
	
	@RequestMapping(value="/getRandom.api")  
	public @ResponseBody BaseResult getRandom(String uid, String type ){
		BaseResult result = new BaseResult();
		result.data=scriptServiceImpl.getRandomFull(uid, type);
		return result;
	}
	
	@RequestMapping(value="/selectlist.api")  
	public @ResponseBody BaseResult   selectlist(String type,String uid,Integer role) {
		BaseResult result = new BaseResult();
		result.dataList=scriptServiceImpl.querySelectList(uid, role, type);
		return result;
	}
	

	@RequestMapping(value="/execute.api")  
	public @ResponseBody BaseResult   execute(String receiver,String  sid) throws Exception {
		BaseResult result = new BaseResult();
		Script script = scriptServiceImpl.getFull(sid);
		HashMap<String,Object> map = new HashMap<String,Object> ();
		HashMap<String,String> params = new HashMap<String,String> ();
		HashMap<String,Object> roles = new HashMap<String,Object> ();
		map.put("script", script);
		for(ScriptCommand c:script.getCommandList()){
			roles.put(c.getRole(), receiver);
		 }
		
		map.put("roles", roles);
		
		params.put("content", JSON.toJSONString(map));
		params.put("receiver",receiver);
		params.put("API_AUTH_KEY","7215EE9C7D9DC229D2921A40E899EC5F");
		
		HttpPostUtils.httpPost(params, ConfigManager.getInstance().getStringValue("SCRIPT_EXECUTE_URL"));
		 
		return result;
	}
	
	
	

	@RequestMapping(value="/rolelist.api")  
	public @ResponseBody BaseResult rolelist(String sid) throws Exception {

		BaseResult result = new BaseResult();
		Script script = scriptServiceImpl.getFull(sid);
		List<String> roleList = new ArrayList<String> ();
		for(ScriptCommand command:script.getCommandList()){
			if(!roleList.contains(command.getRole())){
				roleList.add(command.getRole());
			}
		}
		result.dataList = roleList;
		return result;
	}
	
	
	@RequestMapping(value="/rolesexecute.api")  
	public @ResponseBody BaseResult rolesexecute(String sid,String receiver,String roles) throws Exception {

		BaseResult result = new BaseResult();
		Script script = scriptServiceImpl.getFull(sid);
		script.updateScriptCmdSort();
		HashMap<String,Object> map = new HashMap<String,Object> ();
		HashMap<String,String> params = new HashMap<String,String> ();
		map.put("script", script);
	 
		map.put("roles", JSON.parseObject(roles,new TypeReference<HashMap<String,String>>(){}.getType()));
		
		params.put("content", JSON.toJSONString(map));
		params.put("receiver",receiver);
		params.put("API_AUTH_KEY","7215EE9C7D9DC229D2921A40E899EC5F");
		
		HttpPostUtils.httpPost(params, ConfigManager.getInstance().getStringValue("SCRIPT_EXECUTE_URL"));
		 

		return result;
	}
}
