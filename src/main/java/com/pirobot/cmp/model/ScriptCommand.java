/**
 *  
 * @author 1968877693
 */
package com.pirobot.cmp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/** 
 * This class represents the basic user object.
 *
 */
@Entity
@Table(name = ScriptCommand.TABLE_NAME)
public class ScriptCommand implements Serializable {

    private static final long serialVersionUID = 4733464888738356502L;

    public final static String TABLE_NAME = "t_cmp_script_command";
	@Id
	@Column(name = "cid", length = 32)
	private String cid;
	
	@Column(name = "sid", length = 32)
	private String sid;
	
	@Column(name = "sort")
	private long sort;

    @Column(name = "parent")
    private Long  parent;
    
    @Column(name = "content", length = 1000)
    private String content;	
        
    @Column(name = "action", length = 10)
    private String action;
    
    @Column(name = "role", length = 16)
    private String role;
    
    @Column(name = "prepareTime")
	private long prepareTime;
    
    @Column(name = "delayTime")
   	private long delayTime;
    
	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

 
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public long getPrepareTime() {
		return prepareTime;
	}

	public void setPrepareTime(long prepareTime) {
		this.prepareTime = prepareTime;
	}

	public long getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(long delayTime) {
		this.delayTime = delayTime;
	}

	public long getSort() {
		return sort;
	}

	public void setSort(long sort) {
		this.sort = sort;
	}

	public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}	
	 
	
}
