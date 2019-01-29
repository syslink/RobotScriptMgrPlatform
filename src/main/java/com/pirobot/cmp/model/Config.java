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
@Entity
@Table(name = "t_cmp_config")

/**
 *系统配置
 */
public class Config implements Serializable{
	 
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "gid", length = 32)
	private String gid;
  
	@Column(name = "ikey", length = 32)
	private String key;
	
	@Column(name = "value", length = 2000)
	private String value;
	
	@Column(name = "domain", length = 32)
	private String domain;
	
	@Column(name = "description", length = 200)
	private String description;
	

	
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public String toString()
	{
		StringBuffer buffer = new   StringBuffer();
		buffer.append("{");
		buffer.append("gid:").append("'").append(gid==null?"":gid).append("'");
		buffer.append(",").append("domain:").append("'").append(domain==null?"":domain).append("'");
		buffer.append(",").append("key:").append("'").append(key==null?"":key).append("'");
		buffer.append(",").append("value:").append("'").append(value==null?"":value).append("'");
		buffer.append(",").append("description:").append("'").append(description==null?"":description).append("'");
		buffer.append("}");
		return buffer.toString();
	}
	
	
}
