package com.pirobot.cmp.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pirobot.cmp.dao.impl.RobotRecordDaoImpl;
import com.pirobot.cmp.model.RobotRecord;
import com.pirobot.cmp.service.RobotRecordService;
import com.pirobot.cmp.util.StringUtil;

/**
 * 
 * @author 1968877693
 */
@Service
public class RobotRecordServiceImpl implements RobotRecordService {

	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	private RobotRecordDaoImpl robotRecordDaoImpl;
	
	public void add(RobotRecord record) {
		record.setGid(StringUtil.getUUID());
		record.setTimestamp(System.currentTimeMillis());
		robotRecordDaoImpl.save(record);
	}


	@Override
	public void delete(RobotRecord robotMessage) {
		
	}
 
}
