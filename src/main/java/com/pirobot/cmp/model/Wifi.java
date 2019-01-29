/**
 * @author 1968877693
 */
package com.pirobot.cmp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 */
@Entity
@Table(name = Wifi.TABLE_NAME)
public class Wifi implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String TABLE_NAME="t_cmp_wifi";
	public static String STATE_0 = "0";//未连接
	public static String STATE_1 = "0";//连接
	
	@JSONField(serialize = false)
	@Id
	@Column(name = "gid",length = 32)
	private String gid;
	
	@JSONField(serialize = false)
	@Column(name = "deviceId",length = 32)
	private String deviceId;
	
	@Column(name = "ssid",  length = 32)
	private String ssid;
	 
	@Column(name = "state", length = 1)
	private String state;

	public String getDeviceId() {
		return deviceId;
	}


	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}


	public String getSsid() {
		return ssid;
	}


	public void setSsid(String ssid) {
		this.ssid = ssid;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}
 
	
}
