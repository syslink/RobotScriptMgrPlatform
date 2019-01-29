 
package com.pirobot.cmp.service;

import java.util.List;

import com.pirobot.framework.web.Page;
import com.pirobot.cmp.model.ScriptCommand;
import com.pirobot.cmp.model.Script;


 
public interface ScriptService {

	public void add(Script script);
	public Script get(String sid);
	public Script getFull(String sid);
	public Script getRandomFull(String uid, String cid);
	public void delete(String scriptId);
	
	public void update(Script script);

	public Page queryMePage(String uid,Page page);
	
	public Page queryPage(Script Script,Page page);
	
	public List<ScriptCommand> getCommandList(String sid);
	
	public  List<Script> querySelectList(String uid, Integer role,String type) ;
}
