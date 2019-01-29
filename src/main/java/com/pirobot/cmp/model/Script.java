/**
 *  
 * @author 1968877693
 */
package com.pirobot.cmp.model;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/** 
 *
 */
@Entity
@Table(name = Script.TABLE_NAME)
public class Script implements Serializable {
	public static final String TABLE_NAME = "t_cmp_script"; 
	public static final String STATUS_NORMAL = "1"; 
	public static final String STATUS_NO_CHECK = "0"; 
	
	public static final int STATUS_OPEN = 1; 
	public static final int STATUS_PRIVATE = 0; 
	
    private static final long serialVersionUID = 4733464888738356502L;

	@Id
	@Column(name = "sid", length = 32)
	private String sid;

    @Column(name = "uid", length=20)
    private String uid;
    
    @Column(name = "open")
    private Integer open;
    
    @Column(name = "title",length=32)
    private String title;
    
    @Column(name = "type",length=32)
    private String type;

    @Column(name = "description",length=1024)
    private String description;
    
    @Column(name = "state")
    private String state;
    
    @Column(name = "timestamp")
    private long timestamp;
    
    @Column(name = "roleCount")
    private Integer roleCount;
    
    @Column(name = "banner",length=200)
    private String banner;
    
    
    @Transient
    private List<ScriptCommand> commandList;
    
	public String getSid() {
		return sid;
	}
    

	public long getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}


	public List<ScriptCommand> getCommandList() {
		return commandList;
	}

	public void setCommandList(List<ScriptCommand> commandList) {
		this.commandList = commandList;
	}

	public Integer getRoleCount() {
		return roleCount;
	}


	public void setRoleCount(Integer roleCount) {
		this.roleCount = roleCount;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public void setSid(String sid) {
		this.sid = sid;
	}


	public String getUid() {
		return uid;
	}


	public void setUid(String uid) {
		this.uid = uid;
	}


	 


	public Integer getOpen() {
		return open;
	}


	public void setOpen(Integer open) {
		this.open = open;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getBanner() {
		return banner;
	}


	public void setBanner(String banner) {
		this.banner = banner;
	}

	public void updateScriptCmdSort()
	{
		long factor = new Random().nextInt(100000);
		if(!commandList.isEmpty()){			
			for(int i = 0; i < commandList.size(); i++){
				ScriptCommand tmpCmd = commandList.get(i);
				if(0 != tmpCmd.getParent()){
					commandList.get(i).setParent(tmpCmd.getParent() + factor);
				}
				commandList.get(i).setSort(tmpCmd.getSort() + factor);
			}
		}
	}
}
