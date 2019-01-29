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
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.ReturningWork;
import org.springframework.stereotype.Repository;

import com.pirobot.cmp.model.Wifi;
import com.pirobot.cmp.util.StringUtil;

@SuppressWarnings("unchecked")
@Repository
public class WifiDaoImpl extends HibernateBaseDao<Wifi>{

	 
 
	public void delete(String deviceId) {
		Session session = currentSession();
		Query query = session.createQuery("delete Wifi  where deviceId=:deviceId");
		query.setString("deviceId", deviceId);
		query.executeUpdate();
		 
	}
	
	
	
	public List<Wifi> query(String deviceId) {
		 
		DetachedCriteria criteria = DetachedCriteria.forClass(Wifi.class);
		criteria.add(Restrictions.eq("deviceId",deviceId));
	    criteria.addOrder(Order.desc("state"));
	    return (List<Wifi>) getHibernateTemplate().findByCriteria(criteria);
	}
	 
	
	public void saveList(final List<Wifi> dataList) {
		final Session session = currentSession();
		session.doReturningWork(new ReturningWork<Integer>(){
			@Override
			public Integer execute(Connection conn) throws SQLException{
				int count = 0;
				String sql = "insert into "+Wifi.TABLE_NAME+"(gid,deviceId,ssid,state) values(?,?,?,?)";
				PreparedStatement statement = conn.prepareStatement(sql) ;
				for(Wifi wifi :dataList)
				{
					 
					statement.setString(1, StringUtil.getUUID());
					statement.setString(2, wifi.getDeviceId());
					statement.setString(3, wifi.getSsid());
					statement.setString(4, wifi.getState());
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
