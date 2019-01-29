/**
 * probject:cim
 *  
 * @version 2.0.0
 * @author 3979434@qq.com
 */
package com.pirobot.cmp.service.impl;

import java.util.List;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pirobot.framework.web.Page;
import com.pirobot.cmp.dao.impl.MessageDaoImpl;
import com.pirobot.cmp.model.Message;
import com.pirobot.cmp.service.MessageService;
import com.pirobot.cmp.util.StringUtil;
@Service
public class MessageServiceImpl implements MessageService {
	protected final Logger logger = Logger .getLogger(MessageServiceImpl.class);

	@Autowired
	private MessageDaoImpl messageDao;


	@Override
	public void delete(String gid) {
		Message msg = new Message();
		msg.setMid(gid);
		messageDao.delete(msg);
	}

	@Override
	public Message queryById(String gid) {
		// TODO Auto-generated method stub
		return messageDao.get(gid);
	}

	@Override
	public List<Message> queryMessage(Message mo) {
		// TODO Auto-generated method stub
		return messageDao.queryMessageList(mo);
	}

	@Override
	public List<Message> queryOffLineMessages(String receiver) {
		 Message msg = new Message();
		 msg.setReceiver(receiver);
		 msg.setStatus(Message.STATUS_NOT_RECEIVED);
		 return messageDao.queryMessageList(msg);
	}

	@Override
	public void save(Message obj) {

		if (obj.getMid() == null) {
			obj.setMid(StringUtil.getUUID());
		}
		//9开头的为无需记录的动作消息
		if (obj.isActionMessage()) {
			return;
		}
		messageDao.save(obj);

	}

	@Override
	public void update(Message obj) {
		// TODO Auto-generated method stub
		messageDao.update(obj);
	}

	@Override
	public void updateStatus(String gid, String status) {
		messageDao.updateStatus(gid,status);
	}

	public void setMessageDao(MessageDaoImpl messageDao) {
		this.messageDao = messageDao;
	}

	@Override
	public void deleteUseless() {
		messageDao.deleteUseless();
	}

	@Override
	public void queryPage(Message mo, Page page) {
		int count = this.messageDao.queryMessageAmount(mo);
		page.setCount(Integer.valueOf(count));
		if (page.getCount() == 0) {
			return  ;
		}
		
		this.messageDao.queryByPage(mo, page);
	}

	@Override
	public void save(List<Message> messageList) {
		// TODO Auto-generated method stub
		messageDao.saveList(messageList);
	}

	 

	@Override
	public void deleteByReceiverType(String receiver, String type) {
		// TODO Auto-generated method stub
		messageDao.deleteByReceiverType(receiver,type);
	}

	@Override
	public void deleteBySenderType(String sender, String type) {
		// TODO Auto-generated method stub
		messageDao.deleteBySenderType(sender,type);
	}

	@Override
	public void updateBatchReceived(String account) {
		// TODO Auto-generated method stub
		messageDao.updateBatchReceived(account);
	}
 

}
