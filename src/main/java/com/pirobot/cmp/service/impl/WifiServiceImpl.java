/**
 * probject:cim
 *  
 * @version 2.0.0
 * @author 3979434@qq.com
 */
package com.pirobot.cmp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pirobot.cmp.dao.impl.WifiDaoImpl;
import com.pirobot.cmp.model.Wifi;
import com.pirobot.cmp.service.WifiService;
@Service
public class WifiServiceImpl implements WifiService {
	@Autowired
	private WifiDaoImpl wifiDaoImpl;

	
	public void setWifiDaoImpl(WifiDaoImpl wifiDaoImpl) {
		this.wifiDaoImpl = wifiDaoImpl;
	}

	@Override
	public void save(List<Wifi> list) {
		wifiDaoImpl.delete(list.get(0).getDeviceId());
		wifiDaoImpl.saveList(list);
	}

	@Override
	public List<Wifi> get(String deviceId) {
		// TODO Auto-generated method stub
		return wifiDaoImpl.query(deviceId);
	}
}