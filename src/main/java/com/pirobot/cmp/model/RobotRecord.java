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
@Table(name = "t_cmp_robot_record")
public class RobotRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "gid", length = 32)
	private String gid;
	
	// 机器人编号
	@Column(name = "rid",length = 64)
	private String rid;

	// 用户编号
	@Column(name = "uid", length = 32)
	private String uid;
	
	// 聊天时间
	@Column(name = "timestamp")
	private Long timestamp;
	
	// 用户问题
	@Column(name = "question", length = 128)
	private String question;

	// 用户修正后的问题
	@Column(name = "fixedQuestion", length = 128)
	private String fixedQuestion;
	
	// 机器人答案
	@Column(name = "answer", length = 4096)
	private String answer;
	
	// 机器人答案来源
	@Column(name = "source", length = 1024)
	private String source;




	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	 

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getFixedQuestion() {
		return fixedQuestion;
	}

	public void setFixedQuestion(String fixedQuestion) {
		this.fixedQuestion = fixedQuestion;
	}
	
}
