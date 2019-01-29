/**
 * probject:cim
 *  
 * @version 2.0.0
 * @author 3979434@qq.com
 */
package com.pirobot.cmp.service;

import java.util.List;

import com.pirobot.framework.web.Page;
import com.pirobot.cmp.model.Message;
 
public interface MessageService {
	
	/**
	 * 保存通知信息
	 * @param MessageMO
	 */
	public void save(Message MessageMO);
	
	public void save(List<Message> messageList) ;
	
	/**
	 * 修改通知信息
	 * @param MessageMO
	 */
	public void update(Message MessageMO);
	
	/**
	 * 修改通知信息状态
	 * @param MessageMO
	 */
	public void updateStatus(String gid,String status);
	
	/**
	 * 删除通知
	 * @param id
	 */
	public void delete(String id);
	
	
	/**
	 * 删除通知
	 * @param id
	 */
	public void deleteBySenderType(String sender,String type);
	
	/**
	 * 删除通知
	 * @param id
	 */
	public void deleteByReceiverType(String receiver,String type);
	
	/**
	 * 查看通知
	 * @param id
	 * @return MessageMO
	 */
	public Message queryById(String gid);
	
	 
	
	/**
	 * 根据条件查询通知列表
	 * @param mo 查询条件
	 * @return List<MessageMO>
	 */
	public List<Message> queryMessage(Message mo);
	/**
	 * 查询用户未正常接收的通知
	 * @param mo 查询条件
	 * @return List<MessageMO>
	 */
	public List<Message> queryOffLineMessages(String receiver);
	
	
	/**
	 * 批量删除
	 * @param MessageMOs
	 */
	public void deleteUseless();
	
	
	public void queryPage(Message mo,Page page);

	public void updateBatchReceived(String account);
 
}
