 /**
 * probject:cim
 *  
 * @version 2.0.0
 * @author 3979434@qq.com
 */
package com.pirobot.cmp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pirobot.cmp.util.StringUtil;
 
@Entity
@Table(name = Message.TABLE_NAME)
public class Message implements Serializable {
	public static final String TABLE_NAME="t_cmp_message";
	private static final long serialVersionUID = 1845362556725768545L;
	
	public static final String STATUS_NOT_RECEIVED = "0";//未接受
	public static final String STATUS_RECEIVED = "1";//已接收
	public static final String STATUS_READ = "2";//已读取
	
	
	public static final String ACTION_0 = "0";
	public static final String ACTION_1 = "1";
	public static final String ACTION_2 = "2";
	public static final String ACTION_3 = "3";
	
	
	public Message(){
		
		mid = StringUtil.getUUID();
		timestamp = System.currentTimeMillis();
		status = STATUS_NOT_RECEIVED;
	}
	
    public Message(Object obj){
	}
	
	@Id
	@Column(name = "mid", length = 32)
    private String mid;

    @Column(name = "sender", length = 64)
    private String sender;
    
    @Column(name = "receiver", length = 64)
    private String receiver;
  
    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "content", length = 3200)
    private String content;
    
    //0: 普通消息 1：用户向群发消息 2：系统消息 3：群向用户的消息
    @Column(name = "action")
    private String action;
    
    //0: 未发送 1：已发送 2：已接收 3：已查看
    @Column(name = "status",length = 2)
    private String status;
    
    @Column(name = "format", length = 64)
	private String format;

	@Column(name = "timestamp",updatable = false,length = 13)
	private long timestamp;
	 
    
 

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	 

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	 
	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	//是否为动作消息，无需记录，无需显示
	public boolean isActionMessage()
	{
		
		return action.startsWith("9");
	}
}
