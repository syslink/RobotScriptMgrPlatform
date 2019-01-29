/**
 * @author 1968877693
 */
package com.pirobot.cmp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "t_global_robot")
public class Robot implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "deviceId",length = 32)
	private String deviceId;
	
	@Column(name = "uid",  length = 32)
	private String uid;

	@Column(name = "authUid",  length = 32)
	private String authUid;
	
	@Column(name = "sn", unique=true)
	private Integer sn;

	@Column(name = "name",  length = 32)
	private String name;
	
	//权限类型 HEAD-控制头部,LEG-控制腿,HEAD_LEG-控制头和腿
	@Column(name = "action", length = 32)
	private String action;

	@Column(name = "state", length = 1)
	private String state;
	
	@Column(name = "companyId",  length = 32)
	private String companyId;

	public String getDeviceId() {
		return deviceId;
	}


	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}


	public Integer getSn() {
		return sn;
	}


	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCompanyId() {
		return companyId;
	}


	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


	public String getUid() {
		return uid;
	}


	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAuthUid() {
		return authUid;
	}


	public void setAuthUid(String authUid) {
		this.authUid = authUid;
	}


	public String getAction() {
		return action;
	}


	public void setAction(String action) {
		this.action = action;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}
 
	
}
