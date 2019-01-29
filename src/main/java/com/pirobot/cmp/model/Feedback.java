 /**
 * probject:cim
 * @version 2.0.0
 * 
 * @author 3979434@qq.com
 */
package com.pirobot.cmp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *反馈实体
 */
@Entity
@Table(name = "t_cmp_feedback")
public class Feedback implements Serializable{
 
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "gid", length = 32)
	private String gid;
	@Column(name = "account", length = 32)
	private String account;
	@Column(name = "content", length = 320)
	private String content;
	@Column(name = "appVersion", length = 32)
    private String appVersion;
	@Column(name = "sdkVersion", length = 32)
    private String sdkVersion;
	@Column(name = "deviceModel", length = 32)
    private String deviceModel;
	@Column(name = "timestamp", length = 13)
	private String timestamp;
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	 
	 
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	 
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public String getSdkVersion() {
		return sdkVersion;
	}
	public void setSdkVersion(String sdkVersion) {
		this.sdkVersion = sdkVersion;
	}
	public String getDeviceModel() {
		return deviceModel;
	}
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
	 
	 
    
}
