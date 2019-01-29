 /**
 * probject:cim
 * @version 2.0.0
 * 
 * @author 3979434@qq.com
 */
package com.pirobot.cmp.dao.impl;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.ReturningWork;
import org.springframework.stereotype.Repository;

import com.pirobot.framework.web.Page;
import com.pirobot.cmp.model.Message;
import com.pirobot.cmp.util.StringUtil;

@SuppressWarnings("unchecked")
@Repository
public class MessageDaoImpl extends HibernateBaseDao<Message>{

	 
 
	public Page queryMessageList(Message Message, Page page) {
		 
		DetachedCriteria criteria = mapingParam( Message);
	    criteria.addOrder(Order.desc("timestamp"));
	    page.setDataList(getHibernateTemplate().findByCriteria(criteria,(page.getCurrentPage() - 1) * page.pageSize,page.pageSize));
	    return page;
	}
	
	
	
	public List<Message> queryMessageList(Message Message) {
		 
		DetachedCriteria criteria = mapingParam( Message);
	    criteria.addOrder(Order.asc("timestamp"));
	    return (List<Message>) getHibernateTemplate().findByCriteria(criteria);
	}
	public int queryMessageAmount(Message model) {
		DetachedCriteria criteria = mapingParam(model);
		criteria.setProjection(Projections.rowCount());
		List list = getHibernateTemplate().findByCriteria(criteria);
		return Integer.valueOf(list.get(0).toString());
	}
	
	public void queryByPage(Message message, Page page) {
		DetachedCriteria criteria = mapingParam(message);
		criteria.addOrder(Order.desc("timestamp"));
		page.setDataList(getHibernateTemplate().findByCriteria(criteria,(page.getCurrentPage() - 1) * page.pageSize,page.pageSize));
	}
	
	
	private DetachedCriteria mapingParam(Message model)
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(Message.class);
	   
	    if (!StringUtil.isEmpty(model.getSender()))
	    {
	      criteria.add(Restrictions.eq("sender",model.getSender()));
	    }
	    if (!StringUtil.isEmpty(model.getReceiver()))
	    {
	      criteria.add(Restrictions.eq("receiver",model.getReceiver()));
	    }
	    
	    if (!StringUtil.isEmpty(model.getAction()))
	    {
	         criteria.add(Restrictions.in("action", model.getAction().split(",")));
	    }
	    if (!StringUtil.isEmpty(model.getStatus()))
	    {
	      criteria.add(Restrictions.eq("status",model.getStatus()));
	    }
	  
	    return criteria;
	 }


	public void updateStatus(String mid, String status) {
		
		Session session = currentSession();
		Query query = session.createQuery("update Message set  status=?   where mid=?");
		query.setString(0, status);
		query.setString(1, mid);
		query.executeUpdate();
		
		
	}

	
	public void updateBatchReceived(String account) {
		
		Session session = currentSession();
		Query query = session.createQuery("update Message set  status=?   where status=?  and receiver=?");
		query.setString(0, Message.STATUS_RECEIVED);
		query.setString(1, Message.STATUS_NOT_RECEIVED);
		query.setString(2,account);
		query.executeUpdate();
		
	}
	
	public void deleteUseless() {
		 
		Session session = currentSession();
		Query query = session.createSQLQuery("delete from "+Message.TABLE_NAME+" where (action like '1__' or action like '80_') and status =?");
		query.setString(0, Message.STATUS_RECEIVED);
		query.executeUpdate();
		
	}


	 
	


	public void deleteByReceiverType(String receiver, String action) {
		Session session = currentSession();
		String sql = "delete from "+Message.TABLE_NAME+" where  receiver = :receiver  and action = :action";
		Query query = session.createSQLQuery(sql);
		query.setString("action",action);
		query.setString("receiver",receiver);
		query.executeUpdate();  
		
	}



	public void deleteBySenderType(String sender, String action) {
		Session session = currentSession();
		String sql = "delete from "+Message.TABLE_NAME+" where  sender = :sender  and action = :action";
		Query query = session.createSQLQuery(sql);
		query.setString("action",action);
		query.setString("sender",sender);
		query.executeUpdate();  
		
		
	}
	
	public void saveList(final List<Message> dataList) {
		final Session session = currentSession();
		session.doReturningWork(new ReturningWork<Integer>(){
			@Override
			public Integer execute(Connection conn) throws SQLException{
				int count = 0;
				String sql = "insert into "+Message.TABLE_NAME+"(mid,sender,receiver,format,title,content,action,status,timestamp) values(?,?,?,?,?,?,?,?,?)";
				PreparedStatement statement = conn.prepareStatement(sql) ;
				for(Message message :dataList)
				{
					if (message.isActionMessage()) {
						continue;
					}
					statement.setString(1, message.getMid());
					statement.setString(2, message.getSender());
					statement.setString(3, message.getReceiver());
					statement.setString(4, message.getFormat());
					statement.setString(5, message.getTitle());
					statement.setString(6, message.getContent());
					statement.setString(7, message.getAction());
					statement.setString(8, message.getStatus());
					statement.setLong(10, message.getTimestamp());
					statement.addBatch();
				}
				conn.setAutoCommit(false);
				try{
					count  = statement.executeBatch().length;
				}catch(BatchUpdateException e)
				{
					count = -1;
					e.printStackTrace();
				}finally{
					conn.commit();
				}
				
				return count;
			}
			
		});
	}


}
