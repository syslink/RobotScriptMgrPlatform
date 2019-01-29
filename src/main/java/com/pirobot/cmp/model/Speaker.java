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
@Table(name = Speaker.TABLE_NAME)
public class Speaker implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String TABLE_NAME="t_cmp_speaker";

	@Id
	@JSONField(serialize = false)
	@Column(name = "name",length = 32)
	private String name;
	
	@Column(name = "description",  length = 64)
	private String description;
	 
	@Column(name = "state", length = 1)
	private String state;

	@Column(name = "type", length = 1)
	private String type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
