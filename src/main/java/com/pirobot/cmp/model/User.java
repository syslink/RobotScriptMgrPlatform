 /**
 * probject:cim
 *  
 * @version 1.1.0
 * @author 3979434@qq.com
 */
package com.pirobot.cmp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;



@Entity
@Table(name = User.TABLE_NAME)
public class User implements Serializable {

	private static final long serialVersionUID = 4733464888738356502L;
	
	public static final String TABLE_NAME = "t_global_user";
	
	public static final String OFF_LINE = "0";

	public static final String ON_LINE = "1";
	
	
	@Id
	@Column(name = "uid", length = 32)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long uid;
	
	@Column(name = "account", length = 32 , unique = true)
	private String account;
	
	@JSONField(serialize = false)
	@Column(name = "password", length = 64)
	private String password;

	@Column(name = "name", length = 16)
	private String name;

	@Column(name = "telephone", length = 20)
	private String telephone;
	
	@Column(name = "email", length = 50)
	private String email;
	
	@Column(name = "gender", length = 1)
	private String gender;
 
	@Column(name = "motto", length = 200)
	private String motto ;
	
	@Column(name = "source", length = 32)
	private String source ;

	@Column(name = "state", length = 1)
	private String state ;
	
	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMotto() {
		return motto;
	}

	public void setMotto(String motto) {
		this.motto = motto;
	}


	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@Override
	public String toString() {

		StringBuffer buffer = new   StringBuffer();
		buffer.append("{");
		buffer.append("account:").append("'").append(account==null?"":account).append("'");
		buffer.append(",").append("name:").append("'").append(name==null?"":name).append("'");
		buffer.append(",").append("telephone:").append("'").append(telephone==null?"":telephone).append("'");
		buffer.append(",").append("uid:").append("'").append(uid==null?"":uid).append("'");
		buffer.append(",").append("gender:").append("'").append(gender==null?"":gender).append("'");
		buffer.append(",").append("email:").append("'").append(email).append("'");
		buffer.append("}");
		return buffer.toString();
	}
}
