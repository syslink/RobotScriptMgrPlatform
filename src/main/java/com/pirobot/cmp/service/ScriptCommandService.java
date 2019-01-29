 
package com.pirobot.cmp.service;

import java.util.List;
import com.pirobot.cmp.model.ScriptCommand;
 
public interface ScriptCommandService {

	public void add(ScriptCommand command);
	public void update(ScriptCommand command);
	public ScriptCommand get(String gid);
	public void delete(ScriptCommand command);
	public List<ScriptCommand> getCommandList(String sid);
	public void saveBatch(List<ScriptCommand> commandList);
}
